package com.example.main3.presentation.screen.chat

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.components.Button
import com.example.ui.components.ButtonDef
import com.example.ui.components.InputDef
import com.example.ui.theme.BlackDef
import com.example.ui.theme.GlobalFontFamily
import com.example.ui.theme.Placeholder
import com.example.ui.theme.Primary
import com.example.ui.theme.TextB
import com.example.ui.theme.WhiteDef
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Message(
    val time: String = "",
    val fromLm: Boolean = false,
    val message: String = ""
)


@Composable
fun MessageView(message: Message) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (message.fromLm)
            Arrangement.Start else Arrangement.End
    ) {
        Column(Modifier.fillMaxWidth(0.5f),
            horizontalAlignment = if (message.fromLm)
                Alignment.Start else Alignment.End) {
            MessageBubble(
                message.message,
                !message.fromLm
            )
            Spacer(Modifier.height(4.dp))
            TextB(
                message.time,
                color = Placeholder,
                style = GlobalFontFamily.BodySmall
            )
        }
    }
}

@Composable
fun MessageBubble(text: String, fromUser: Boolean) {
    val baseDp = 20.dp
    val notBaseDp = 4.dp
    val shape = if (fromUser) AbsoluteRoundedCornerShape(
        topLeft = baseDp,
        topRight = baseDp, bottomLeft = baseDp, bottomRight = notBaseDp
    ) else AbsoluteRoundedCornerShape(
        topLeft = baseDp,
        topRight = baseDp, bottomLeft = notBaseDp, bottomRight = baseDp
    )

    val color = if (fromUser) Primary else WhiteDef

    val textC = if (fromUser) WhiteDef else BlackDef

    Box(
        Modifier
            .clip(shape)
            .background(
                color,
                shape
            )
            .border(
                if (fromUser) 0.dp else 1.dp,
                Placeholder,
                shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.padding(16.dp),
            contentAlignment = Alignment.Center) {
            TextB(
                text,
                maxLines = 999,
                color = textC,
                style = GlobalFontFamily.FieldLabel
            )
        }
    }
}


@SuppressLint("SdCardPath")
@Composable
fun ChatScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val format = DateTimeFormatter.ofPattern("hh:mm a", Locale.US)
    fun now() = LocalDateTime.now().format(format)

    var message by remember { mutableStateOf("") }
    var messageList = remember { mutableStateListOf<Message>() }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf<Boolean>(false) }

    var llm = remember {
        runCatching {
            LlmInference.createFromOptions(
                context, LlmInference.LlmInferenceOptions.builder()
                    .setModelPath("/data/data/com.example.main3/files/qwen.task")
                    .setMaxTokens(512)
                    .build()
            )
        }.getOrNull()
    }

    fun sentMessage() {
        val text = message.ifEmpty { return }
        val model = llm ?: run { error = "llm not init"; return}

        messageList += Message(now(),false,text)
        message = ""
        isLoading = true
        error = null

        scope.launch {
            runCatching {
                withContext(Dispatchers.Default) { model.generateResponse(text)}
            }.onSuccess {
                messageList += Message(now(),true,it)
            }.onFailure {
                error = it.message
            }
            isLoading = false
        }
    }

    Scaffold(
        bottomBar = {
            InputDef(
                message,
                {
                    message = it
                },
                placeholder = "Ai is thinking...",
                trailingIcon = {
                    Icon(
                        painterResource(R.drawable.eye),
                        contentDescription = null,
                        modifier = Modifier.clickable(enabled = !isLoading) {
                            sentMessage()
                        }
                    )
                }
            )
        }
    ) { inne ->
        LazyColumn(Modifier
            .fillMaxWidth()
            .padding(inne)
            .padding(horizontal = 20.dp)) {
            items(messageList) {
                MessageView(it)
            }
            if (isLoading) { item {
                    MessageView(
                        Message(
                            "",
                            true,
                            "Inference in progress..."
                        )
                    )
                }
            }
            error?.let {
                item {
                    TextB(it,color = Color.Red)
                    ButtonDef("retry",
                        {
                            messageList.removeLastOrNull()
                            sentMessage()
                        },
                        Button.Error)
                }
            }
        }
    }
}