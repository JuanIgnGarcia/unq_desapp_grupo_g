package com.example.demo.model

enum class TransactionStatus {

    ACTIVE,
    CLOSE,
    CANCEL;

    fun isActive():Boolean{
        return when (this) {
            ACTIVE  ->true
            else -> {
                false
            }
        }
    }


}