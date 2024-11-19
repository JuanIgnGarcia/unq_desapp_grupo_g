package com.example.demo


import com.example.demo.model.*
import com.example.demo.request.UserOfferRequest
import com.example.demo.service.CryptoService
import com.example.demo.service.TransactionService
import com.example.demo.service.UserOfferService
import com.example.demo.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.aot.generate.Generated
import org.springframework.context.annotation.Profile

@Generated
@Component
@Profile("!test")
class DataLoader(private val userService: UserService,
                 private val userOfferService : UserOfferService,
                 private val cryptoService:CryptoService,
                 private val transactionService : TransactionService
) : CommandLineRunner {


    override fun run(vararg args: String?) {

        // Cargar datos iniciales
        val userTest1 = User.UserBuilder()
                .name("Marcelo")
                .lastName("Jorge")
                .email("Marcelo.Jorge@example.com")
                .address("234 Second St")
                .password("ResponsabilidaD1!")
                .cvuMercadoPago("2222222222222222222222")
                .cryptoAddress("11111111")
                .point(0)
                .mountCompletedTransactions(0)
                .build()
        val userTest2 = User.UserBuilder()
                .name("Joaquin")
                .lastName("Villanueva")
                .email("Joaquin.Villanueva@example.com")
                .address("567 Big Bank")
                .password("ContraproducentE1!")
                .cvuMercadoPago("3333333333333333333333")
                .cryptoAddress("87654321")
                .point(0)
                .mountCompletedTransactions(0)
                .build()

        val offerTest1 = UserOfferRequest( cryptoSymbol = "ALICEUSDT",
                cryptoMounts = 20.0,
                cryptoPrice = cryptoService.getCrypto("ALICEUSDT").price.toDouble(),
                userId= "1",
                offerType = "SELL")

        val offerTest2 = UserOfferRequest( cryptoSymbol = "AAVEUSDT",
                cryptoMounts = 20.0,
                cryptoPrice = cryptoService.getCrypto("AAVEUSDT").price.toDouble(),
                userId= "1",
                offerType = "BUY")

        val offerTest3 = UserOfferRequest( cryptoSymbol = "NEOUSDT",
                cryptoMounts = 20.0,
                cryptoPrice =cryptoService.getCrypto("NEOUSDT").price.toDouble(),
                userId= "2",
                offerType = "BUY")


        userService.createUser(userTest1)
        userService.createUser(userTest2)
        userOfferService.publishOffer(offerTest1)
        userOfferService.publishOffer(offerTest2)
        userOfferService.publishOffer(offerTest3)
        transactionService.createTransaction("2","1")


    }
}
