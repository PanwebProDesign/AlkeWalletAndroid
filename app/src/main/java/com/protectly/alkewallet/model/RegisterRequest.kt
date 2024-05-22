package com.protectly.alkewallet.model

data class RegisterRequest(
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val roleId : Long = 2,
    val points : Long = 50
)
