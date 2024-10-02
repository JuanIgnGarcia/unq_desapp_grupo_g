package com.example.demo.request

class UserOfferRequest(val cryptoSymbol: String,
                       val cryptoMounts: Double,
                       val cryptoPrice:  Double,
                       val userId :      String,
                       val offerType:    String) {}
