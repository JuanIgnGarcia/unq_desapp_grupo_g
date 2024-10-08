package com.example.demo.dto

import java.util.Date

class TransactionPeriodDTO (val date : Date,
                            val totalAmount: Double,
                            val totalAmountArgs :Double,
                            val cryptos: List<CryptoTransactionDto>,
) {}

