package com.example.demo.model

import java.util.Date

data class CryptoPrice(
    val symbol: String,
    val price: String,
    val date: Date
)