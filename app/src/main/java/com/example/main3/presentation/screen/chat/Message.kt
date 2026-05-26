package com.example.main3.presentation.screen.chat

data class Message (
    val time: String,
    val message: String,
    val fromLLm: Boolean
)