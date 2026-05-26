package com.example.main3.presentation.screen.chat

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.net.common.ContextHolder
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ChatViewModel(): ViewModel() {
    private val format = DateTimeFormatter.ofPattern("hh:mm a", Locale.US)
    fun now() = LocalTime.now().format(format)

    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    val messages = mutableStateListOf<Message>()

    val llm = runCatching {
        LlmInference.createFromOptions(
            ContextHolder.getC(),
            LlmInference.LlmInferenceOptions.builder()
                .setModelPath("/data/data/com.example.main3/files/qwen.task")
                .build()
        )
    }.getOrNull()

    fun init() {
        send(ChatConf.firstPromt,true)
    }

    fun send(text: String, isFirst: Boolean = false) {
        val model = llm ?: run {
            error = "no llm init"
            return
        }
        error = null
        isLoading = true
        if (!isFirst) {
            messages += Message(now(),text,false)
        }
        viewModelScope.launch {
            delay(5000L)
            runCatching {
                withContext(Dispatchers.Default) {
                    model.generateResponse(text)
                }
            }.onSuccess {
                messages += Message(now(),it,true)
            }.onFailure {
                error = it.message
            }
            isLoading=false
        }
    }

    fun repeat() {
        messages.removeLastOrNull()
        send(messages.lastOrNull()?.message ?: return)
    }

}