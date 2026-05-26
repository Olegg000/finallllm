package com.example.main3.domain.remote

import com.example.net.types.ResultType
import org.openapitools.client.models.AuthRequest
import org.openapitools.client.models.AuthResponse
import org.openapitools.client.models.RegisterRequest
import org.openapitools.client.models.RegisterUser200Response
import org.openapitools.client.models.Success
import org.openapitools.client.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthRemote {
    /**
     * POST auth
     * Авторизация
     *
     * Responses:
     *  - 200: Token and user profile
     *  - 401: Error response
     *
     * @param authRequest
     * @return [AuthResponse]
     */
    @POST("auth")
    suspend fun authLogin(@Body authRequest: AuthRequest): ResultType<AuthResponse>

    /**
     * GET auth/profileByToken
     * Получение профиля по токену
     * Передайте токен из POST /auth через Authorize в Swagger или заголовок Authorization: Bearer &lt;token&gt;. Сервер также принимает ?token&#x3D;&lt;token&gt; и X-Auth-Token для удобства отладки.
     * Responses:
     *  - 200: Current user
     *  - 401: Error response
     *
     * @param token Optional debug-friendly token transport. (optional)
     * @return [User]
     */
    @GET("auth/profileByToken")
    suspend fun getProfileByToken(@Query("token") token: String? = null): ResultType<User>

    /**
     * GET logout
     * Выход
     *
     * Responses:
     *  - 200: Logout result
     *
     * @return [Success]
     */
    @GET("logout")
    suspend fun logout(): ResultType<Success>

    /**
     * POST register
     * Регистрация
     *
     * Responses:
     *  - 200: Registered user
     *  - 409: Error response
     *  - 422: Error response
     *
     * @param registerRequest
     * @return [RegisterUser200Response]
     */
    @POST("register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): ResultType<RegisterUser200Response>

}