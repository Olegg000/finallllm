package com.example.main3.domain.useCases

import com.example.main3.domain.remote.AuthRemote
import com.example.main3.domain.validator.RegValidator
import com.example.net.types.ResultType
import org.openapitools.client.models.RegisterRequest
import org.openapitools.client.models.RegisterUser200Response

class RegUseCase(
    val authRemote: AuthRemote
) {

    suspend operator fun invoke(
        registerRequest: RegisterRequest,
        password2: String
    ): ResultType<RegisterUser200Response> {

        if (RegValidator.passwordCheck(registerRequest)) {
            throw IllegalStateException("password didnt valid")
        }

        if (RegValidator.passwordMatch(registerRequest, password2)) {
            throw IllegalStateException("password didnt match")
        }

        if (RegValidator.mailCheck(registerRequest)) {
            throw IllegalStateException("mail didnt valid")
        }

        if (RegValidator.passwordShort(registerRequest)) {
            throw IllegalStateException("password short")
        }

        return authRemote.registerUser(registerRequest)
    }

}