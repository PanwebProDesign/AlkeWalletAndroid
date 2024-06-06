package com.protectly.alkewallet.model.network


import com.protectly.alkewallet.model.AccountRequest
import com.protectly.alkewallet.model.AccountResponse
import com.protectly.alkewallet.model.LoginRequest
import com.protectly.alkewallet.model.LoginResponse
import com.protectly.alkewallet.model.PaymentRequest
import com.protectly.alkewallet.model.PaymentResponse
import com.protectly.alkewallet.model.RegisterRequest
import com.protectly.alkewallet.model.RegisterResponse
import com.protectly.alkewallet.model.Transaction
import com.protectly.alkewallet.model.TransactionsResponse
import com.protectly.alkewallet.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @Headers("Content-Type: application/json")
    @POST("users")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @GET("auth/me") //lo vemos con UserResponse
    suspend fun getUserData(@Header("Authorization") token: String): Response<UserResponse>

    @GET("accounts/me")
    suspend fun getAccount(@Header("Authorization") authHeader: String): Response<List<AccountResponse>>

    @POST("accounts")
    suspend fun createAccount(
        @Header("Authorization") authHeader: String,
        @Body accountRequest: AccountRequest
    ): Response<AccountResponse>

    @GET("transactions")
    suspend fun getTransactions(@Header("Authorization") authHeader: String): Response<TransactionsResponse>

    @POST("accounts/{accountId}")
    suspend fun sendPayment(
        @Header("Authorization") authHeader: String,
        @Path("accountId") accountId: Int,
        @Body paymentRequest: PaymentRequest
    ): Response<PaymentResponse>

}
