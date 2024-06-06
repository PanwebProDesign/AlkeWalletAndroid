package com.protectly.alkewallet.model

data class PaymentRequest(
    val type: String,
    val concept: String,
    val amount: Double
)
