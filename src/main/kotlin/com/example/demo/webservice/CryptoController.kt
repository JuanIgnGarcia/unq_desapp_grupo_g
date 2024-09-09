package com.example.demo.webservice

import com.example.demo.model.CryptoPrice
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.example.demo.service.CryptoService

@RestController
class CryptoController {

    @Autowired
    lateinit var service: CryptoService

    @GetMapping("/cryptos")
    fun getCryptosValue(): List<CryptoPrice> {
        return service.allCryptos()
    }
}