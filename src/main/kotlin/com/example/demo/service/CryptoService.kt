package com.example.demo.service

import com.example.demo.Exceptions.CryptoNotFoundException
import com.example.demo.model.CryptoPrice
import com.example.demo.service.Proxys.ProxyBinance
import org.springframework.stereotype.Service

@Service
class CryptoService {

    private val proxyBinance = ProxyBinance()

    private val cryptosNames: List<String> = listOf(
        "ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT",
        "NEOUSDT", "DOTUSDT", "ETHUSDT", "CAKEUSDT", "BTCUSDT",
        "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT"
    )

    fun allCryptos(): List<CryptoPrice> {
        try {

            val cryptoPrices = proxyBinance.cryptosPrices(cryptosNames)
            return cryptoPrices

        }catch (e: Exception) {
            throw CryptoNotFoundException("Internal error fetching prices")
        }

    }

    fun getCrypto(cryptoName: String) : CryptoPrice {
        try {
            val cryptoPrices = proxyBinance.cryptosPrices(cryptosNames)
            return cryptoPrices.first()

        }catch (e: Exception) {
            throw CryptoNotFoundException("Error fetching price for $cryptoName: ${e.message}")
        }
    }


}

