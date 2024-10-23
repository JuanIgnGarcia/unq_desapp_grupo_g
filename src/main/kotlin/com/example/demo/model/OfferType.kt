package com.example.demo.model

import com.example.demo.exceptions.OfferTypeException

enum class OfferType {
    BUY,
    SELL;

    fun isABuy(): Boolean {
        return when (this) {
            BUY  -> true
            else -> false
        }
    }

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