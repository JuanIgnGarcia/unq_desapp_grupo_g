package com.example.demo


import com.example.demo.model.*
import com.example.demo.request.UserOfferRequest
import com.example.demo.security.AuthUserDTO
import com.example.demo.security.AuthUserService
import com.example.demo.security.TokenDTO
import com.example.demo.service.CryptoService
import com.example.demo.service.TransactionService
import com.example.demo.service.UserOfferService
import com.example.demo.service.UserService
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
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
                 private val transactionService : TransactionService,
                 private val authUserService: AuthUserService
) : CommandLineRunner {


    override fun run(vararg args: String?) {

        val adminUserDTO = AuthUserDTO(userName = "admin", password = "admin")
        authUserService.save(adminUserDTO)

        // Generar token JWT para el usuario admin
        val tokenDTO: TokenDTO? = authUserService.login(adminUserDTO)

        if (tokenDTO != null) {
            logger.info("Token JWT generado para el usuario 'admin': ${tokenDTO.token}")
        } else {
            logger.info("No se pudo generar el token para el usuario 'admin'")
        }

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
        transactionService.createTransaction("2","1")


    }
}
