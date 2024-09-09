package com.example.demo.service

import com.example.demo.model.CryptoPrice
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.Date

@Service
class CryptoService {

    private val restTemplate = RestTemplate()

    private val cryptosNames: List<String> = listOf(
        "ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT",
        "NEOUSDT", "DOTUSDT", "ETHUSDT", "CAKEUSDT", "BTCUSDT",
        "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT"
    )

    fun allCryptos(): List<CryptoPrice> {
        val cryptoPrices = mutableListOf<CryptoPrice>()
        val baseUrl = "https://api.binance.com/api/v3/ticker/price?symbol="

        for (crypto in cryptosNames) {
            val url = "$baseUrl$crypto"
            val response: Map<*, *>? = restTemplate.getForObject(url, Map::class.java)
            //val response: Map<String, Any>? = restTemplate.getForObject(url, Map::class.java)

            response?.let {
                val symbol = it["symbol"] as String
                val price = it["price"] as String
                val date = Date()
                cryptoPrices.add(CryptoPrice(symbol, price, date))
            }
        }

        return cryptoPrices
    }
}

