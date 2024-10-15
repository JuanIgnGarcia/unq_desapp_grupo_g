package com.example.demo

import com.example.demo.model.*
import com.example.demo.request.UserOfferRequest
import com.example.demo.service.CryptoService
import com.example.demo.service.Proxys.ProxyBinance
import com.example.demo.service.TransactionService
import com.example.demo.service.UserOfferService
import com.example.demo.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataLoader(private val userService: UserService, private val userOfferService : UserOfferService, private val cryptoService:CryptoService, private val transactionService : TransactionService) : CommandLineRunner {

    //val proxyBinance = ProxyBinance()

    override fun run(vararg args: String?) {
        // Cargar datos iniciales
        val user1 = User.UserBuilder()
                .name("Marcos")
                .lastName("Dias")
                .email("Marcos.dias@example.com")
                .address("123 Main Street")
                .password("StrongPassword1!")
                .cvuMercadoPago("1234567890123456789012")
                .cryptoAddress("12345678")
                .point(0)
                .mountCompletedTransactions(0)
                .build()
        val user2 = User.UserBuilder()
                .name("Joaquin")
                .lastName("Villanueva")
                .email("Joaquin.Villanueva@example.com")
                .address("123 Main Street")
                .password("StrongPassword1!")
                .cvuMercadoPago("1111111111111111111111")
                .cryptoAddress("11111111")
                .point(0)
                .mountCompletedTransactions(0)
                .build()

        //ofertas

        val offer1 = UserOfferRequest( cryptoSymbol = "ALICEUSDT",
                cryptoMounts = 10.0,
                //cryptoPrice = proxyBinance.cryptosPrices(listOf("ALICEUSDT")).first().price.toDouble(),
                cryptoPrice = cryptoService.getCrypto("ALICEUSDT").price.toDouble(),
                userId= "1",
                offerType = "SELL")

        val offer2 = UserOfferRequest( cryptoSymbol = "AAVEUSDT",
                cryptoMounts = 10.0,
                cryptoPrice = cryptoService.getCrypto("AAVEUSDT").price.toDouble(),
                userId= "1",
                offerType = "BUY")

        val offer3 = UserOfferRequest( cryptoSymbol = "NEOUSDT",
                cryptoMounts = 20.0,
                cryptoPrice =cryptoService.getCrypto("NEOUSDT").price.toDouble(),
                userId= "2",
                offerType = "BUY")

        //transaction



        userService.createUser(user1)
        userService.createUser(user2)
        userOfferService.publishOffer(offer1)
        userOfferService.publishOffer(offer2)
        userOfferService.publishOffer(offer3)
        //transactionService.createTransaction("2","1")


    }
}