package com.example.demo.webservice

import com.example.demo.Exceptions.CryptoNotFoundException
import com.example.demo.model.CryptoPrice
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.example.demo.service.CryptoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@RestController
class CryptoController {

    @Autowired
    lateinit var service: CryptoService

    @GetMapping("/cryptos")
    fun getCryptosValue(): ResponseEntity<List<CryptoPrice>> {
        return try {
            val cryptoPrice = service.allCryptos()
            ResponseEntity.ok(cryptoPrice)
        } catch (e: CryptoNotFoundException) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @GetMapping("/crypto/{name}")
    fun getCryptoValue(@PathVariable name: String): ResponseEntity<CryptoPrice> {
        return try {
            val upperCaseName : String = name.uppercase(Locale.getDefault())
            val cryptoPrice = service.getCrypto(upperCaseName)
            ResponseEntity.ok(cryptoPrice)
        } catch (e: CryptoNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }


}