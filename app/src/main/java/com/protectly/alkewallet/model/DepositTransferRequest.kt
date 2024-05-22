package com.protectly.alkewallet.model

data class DepositTransferRequest (
    val type : String,
    val concept : String,
    val amonut : Double
)