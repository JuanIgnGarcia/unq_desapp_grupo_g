package com.example.demo.webservice

import com.example.demo.model.CryptoPrice
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.example.demo.service.CryptoService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@RestController
class CryptoController {

    @Autowired
    lateinit var service: CryptoService

    @Operation(summary = "Get all cryptocurrencys prices")
    @GetMapping("/cryptos")
    fun getCryptosValue(): ResponseEntity<List<CryptoPrice>> {
        val cryptoPrice = service.allCryptos()
        return ResponseEntity.ok(cryptoPrice)
    }

    @Operation(summary = "Get a cryptocurrency price")
    @GetMapping("/crypto/{symbol}")
    fun getCryptoValue(@PathVariable symbol: String): ResponseEntity<CryptoPrice> {
        val upperCaseName : String = symbol.uppercase(Locale.getDefault())
        val cryptoPrice = service.getCrypto(upperCaseName)
        return ResponseEntity.ok(cryptoPrice)
    }
}