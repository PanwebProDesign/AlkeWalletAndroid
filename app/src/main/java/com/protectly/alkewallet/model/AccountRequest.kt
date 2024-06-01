package com.protectly.alkewallet.model

data class AccountRequest(
    val creationDate: String,
    val money: Double = 150000.0,
    val isBlocked: Boolean = false,
    val userId: Int
)
