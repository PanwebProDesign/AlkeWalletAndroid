package com.protectly.alkewallet.model

data class LoginResponse(
    val accessToken: String?,
    val error: String?,
    val status: Int?
)