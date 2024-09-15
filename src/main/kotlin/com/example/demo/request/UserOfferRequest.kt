package com.example.demo.request

class UserOfferRequest(val cryptoSymbol: String,
                       val cryptoMounts: Float,
                       val cryptoPrice:  Float,
                       val argsMounts:   Float,
                       val userName:     String,
                       val userLastName: String,
                       val offerType:    String) {}
