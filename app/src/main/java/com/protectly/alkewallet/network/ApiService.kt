package com.protectly.alkewallet.network

import com.protectly.alkewallet.model.LoginRequest
import com.protectly.alkewallet.model.LoginResponse
import com.protectly.alkewallet.model.RegisterRequest
import com.protectly.alkewallet.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @Headers("Content-Type: application/json")
    @POST("users")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
}
