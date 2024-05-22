package com.protectly.alkewallet.model

data class ListUserResponse(
    val first_name: String?,
    val last_name: String?,
    val email: String?,
    val password: String?,
    val roleId : Long?,
    val points : Long?,
    val error : String?,
    val status : Long?
)
