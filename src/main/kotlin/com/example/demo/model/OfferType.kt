package com.example.demo.model

import com.example.demo.Exceptions.OfferTypeException

enum class OfferType {
    BUY,
    SELL
}

object OfferTypeHelper {

    fun transform(type:String): OfferType {
        return when (type.uppercase()) {
            "BUY"  -> OfferType.BUY
            "SELL" -> OfferType.SELL
            else -> {
                throw OfferTypeException("Invalid Offer type")
            }
        }
    }

}