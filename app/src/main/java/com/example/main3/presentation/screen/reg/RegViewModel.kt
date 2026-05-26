package com.example.main3.presentation.screen.reg

import com.example.main3.data.api.AuthApiMain
import com.example.main3.domain.remote.AuthRemote
import com.example.main3.domain.useCases.RegUseCase
import com.example.main3.presentation.screen.base.BaseViewModel
import org.openapitools.client.models.RegisterRequest

class RegViewModel(
    val authRemote: AuthRemote = AuthApiMain(),
    val regUseCase: RegUseCase = RegUseCase(authRemote)
): BaseViewModel<RegUiState>(RegUiState()) {
    
    
    val firstName = field<String> { copy(firstName = it) }
    val lastName = field<String> { copy(lastName = it) }
    val email = field<String> { copy(email = it) }
    val password = field<String> { copy(password = it) }
    val password2 = field<String> { copy(password2 = it) }
    val role = field<String> { copy(role = it) }
    val checked = field<Boolean> { copy(isChecked = it) }

    fun reg() {
        launchWithStatus(
            {
                regUseCase(
                    registerRequest = RegisterRequest(
                        state.value.firstName ,
                        state.value.lastName ,
                        state.value.email ,
                        state.value.password ,
                        state.value.role
                    ),
                    password2 = state.value.password2
                )
            },
            {
                navigate("login")
                copy()
            }
        )
    }
    
}