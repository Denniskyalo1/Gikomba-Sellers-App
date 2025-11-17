package com.example.thriftlink.model

data class Payment(
    val amount: Double,
    val status: String,
    val date: String
)
