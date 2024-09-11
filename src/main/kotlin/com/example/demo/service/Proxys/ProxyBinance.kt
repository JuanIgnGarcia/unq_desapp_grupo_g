package com.example.demo.service.Proxys

import com.example.demo.model.CryptoPrice
import org.springframework.web.client.RestTemplate
import java.util.*

class ProxyBinance {

    private val restTemplate = RestTemplate()
    private val baseUrl = "https://api.binance.com/api/v3/ticker/price?symbol="

    fun cryptosPrices(cryptosNames: List<String>): List<CryptoPrice> {
        val cryptoPricesList = mutableListOf<CryptoPrice>()

        for (crypto in cryptosNames) {
            val url = "$baseUrl$crypto"
            val response: Map<*, *>? = restTemplate.getForObject(url, Map::class.java)

            response?.let {
                val symbol = it["symbol"] as String
                val price = it["price"] as String
                val date = Date()
                cryptoPricesList.add(CryptoPrice(symbol, price, date))
            }
        }
        return cryptoPricesList
    }

}