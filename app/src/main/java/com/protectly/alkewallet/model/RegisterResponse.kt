package com.protectly.alkewallet.model

data class RegisterResponse(
    val roleId: Long?,
    val id: Long?,
    val first_name: String?,
    val last_name: String?,
    val email: String?,
    val password: String?,
    val points : Long?,
    val updatedAt : String?,
    val createdAt : String?,
    val error : String?,
    val status : Long?
)
