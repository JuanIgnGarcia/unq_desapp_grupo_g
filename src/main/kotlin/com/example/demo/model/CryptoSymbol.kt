package com.example.demo.model

import com.example.demo.exceptions.CryptoNotFoundException


enum class CryptoSymbol {
    ALICEUSDT,
    MATICUSDT,
    AXSUSDT,
    AAVEUSDT,
    ATOMUSDT,
    NEOUSDT,
    DOTUSDT,
    ETHUSDT,
    CAKEUSDT,
    BTCUSDT,
    BNBUSDT,
    ADAUSDT,
    TRXUSDT,
    AUDIOUSDT
}



object CryptoSymbolHelper {

    fun cryptoSymbols(): List<String> {
        return CryptoSymbol.entries.map { it.name }
    }

    fun validateCriptoSymbol(cryptoSymbol: String) {
        val validSymbols = cryptoSymbols()
        if (!validSymbols.contains(cryptoSymbol)) {
            throw CryptoNotFoundException("Crypto symbol $cryptoSymbol is not valid.")
        }
    }

}