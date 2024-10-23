package com.example.demo.model


enum class OfferStatus{
    AVAILABLE,
    UNVAILABLE;


    fun isAvailable(): Boolean {
        return when (this) {
            AVAILABLE  -> true
            else -> {
                false
            }
        }
    }
}