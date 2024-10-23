package com.example.demo.dto

import org.springframework.aot.generate.Generated

@Generated
class CryptoTransactionDto (val cryptoSymbol:  String,
                            val cryptoAmount:   Double,
                            val cryptoPrice:    Double,
                            val cryptoPriceArg: Double
) {}