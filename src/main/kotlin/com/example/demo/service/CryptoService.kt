package com.example.demo.service

import com.example.demo.exceptions.CryptoNotFoundException
import com.example.demo.model.CryptoPrice
import com.example.demo.model.CryptoSymbolHelper
import com.example.demo.service.Proxys.ProxyBinance
import org.springframework.stereotype.Service
import org.springframework.aot.generate.Generated
import org.springframework.transaction.annotation.Transactional

@Generated
@Service
@Transactional
class CryptoService {

    private val proxyBinance = ProxyBinance()

    fun allCryptos(): List<CryptoPrice> {
        try {
            val cryptoSymbols = CryptoSymbolHelper.cryptoSymbols()
            val cryptoPrices = proxyBinance.cryptosPrices(cryptoSymbols)
            return cryptoPrices

        }catch (e: Exception) {
            throw CryptoNotFoundException("Internal error fetching prices")
        }

    }

    fun getCrypto(cryptoSymbol: String) : CryptoPrice {

        try {
            CryptoSymbolHelper.validateCriptoSymbol(cryptoSymbol)
            val cryptoPrices = proxyBinance.cryptosPrices(listOf(cryptoSymbol))
            return cryptoPrices.first()

        }catch (e: Exception) {
            throw CryptoNotFoundException("Error fetching price for $cryptoSymbol: ${e.message}")
        }
    }


}

