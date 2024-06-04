package com.protectly.alkewallet.model

data class Transaction(
    val id: Int,
    val amount: String,
    val concept: String,
    val date: String,
    val type: String,
    val accountId: Int,
    val userId: Int,
    val toAccountId: Int,
    val createdAt: String,
    val updatedAt: String,
    val name: String?,
    val lastName: String?,
    val imgUrl : String?
)
