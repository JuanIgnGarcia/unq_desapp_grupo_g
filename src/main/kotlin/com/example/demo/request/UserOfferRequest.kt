package com.example.demo.request

class UserOfferRequest(val cryptoSymbol: String,
                       val cryptoMounts: Double,
                       val cryptoPrice:  Double,
                       val argsMounts:   Double,
                       val userName:     String,
                       val userLastName: String,
                       val offerType:    String) {}
