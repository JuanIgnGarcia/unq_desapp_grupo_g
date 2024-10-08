package com.example.demo.request
import org.springframework.aot.generate.Generated

@Generated
class UserOfferRequest(val cryptoSymbol: String,
                       val cryptoMounts: Double,
                       val cryptoPrice:  Double,
                       val userId :      String,
                       val offerType:    String) {}
