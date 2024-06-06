package com.protectly.alkewallet.model

data class AccountResponse(
    val id: Int,
    val creationDate: String?,
    val money: Double?,
    val isBlocked: Boolean?,
    val userId: Int?,
    val error: Int?,
    val status: Int?
)
