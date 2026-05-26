package com.example.main3.presentation.screen.reg

import org.openapitools.client.models.RegisterRequest

data class RegUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val password2: String = "",
    val role: String = "",
    val isChecked: Boolean =false
    )