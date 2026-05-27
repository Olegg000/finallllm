package com.example.main3.presentation.screen.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.main3.R
import com.example.ui.components.Button
import com.example.ui.components.ButtonDef
import com.example.ui.components.InputDef

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel()
) {
    var input by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.init()
    }

    Scaffold(
        bottomBar = {
            InputDef(
                input,
                { input = it },
                trailingIcon = {
                    Icon(
                        painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            viewModel.send(input.ifEmpty { return@clickable })
                            input = ""
                        }
                    )
                }
            )
        }
    ) { inn ->
        LazyColumn(Modifier
            .padding(inn)
            .padding(20.dp)) {
            items(viewModel.messages) {
                Bubble(it)
            }
            if (viewModel.isLoading) {
                item {
                    Bubble(
                        Message(
                            "",
                            "Inference in progress...",
                            true
                        )
                    )
                }
            }
            viewModel.error?.let {
                item {
                    Text(it, color = Color.Red)
                    ButtonDef(
                        "repeat", {
                            viewModel.repeat()
                        },
                        Button.Error
                    )
                }
            }
        }
    }
}