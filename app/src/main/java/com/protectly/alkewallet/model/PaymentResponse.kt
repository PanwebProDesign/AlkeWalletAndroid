package com.protectly.alkewallet.model

data class PaymentResponse(
    val message: String?,
    val error: String?,
    val status: Int?
)
