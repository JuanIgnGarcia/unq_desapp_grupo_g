package com.example.demo.model

//import com.example.demo.exceptions.OfferTypeException
import java.util.concurrent.TimeoutException

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
                throw TimeoutException("Invalid Offer type")//OfferTypeException("Invalid Offer type")
            }
        }
    }

}