package com.example.demo.dto

import java.util.Date
import org.springframework.aot.generate.Generated

@Generated
class TransactionPeriodDTO (val date : Date,
                            val totalAmount: Double,
                            val totalAmountArgs :Double,
                            val cryptos: List<CryptoTransactionDto>,
) {}

