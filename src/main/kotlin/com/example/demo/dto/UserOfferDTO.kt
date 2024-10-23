package com.example.demo.dto

import org.springframework.aot.generate.Generated

@Generated
class UserOfferDTO(val userOfferId : String,
                   val cryptoSymbol: String,
                   val cryptoMounts: Double,
                   val cryptoPrice:  Double,
                   val argsMounts:   Double,
                   val userName:     String,
                   val userLastName: String,
                   val offerDate:    String,
                   val offerType:    String) {

}
