package com.example.demo

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    private val auditInterceptor = AuditInterceptor()

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(auditInterceptor)
            .addPathPatterns(
                // CryptoController
                "/cryptos",
                "/crypto/{symbol}",

                // TransactionController
                "/transaction/AcceptOffer/{userId}/{offerId}",
                "/transaction/makeTransfer/{userId}/{transactionId}",
                "/transaction/confirmReceipt/{userId}/{transactionId}",
                "/transaction/cancel/{userId}/{transactionId}",
                "/transactions",
                "/transactions/period",

                // UserController
                "/register",
                "/users",

                // UserOfferController
                "/publish",
                "/offers",
                "/offers/{userId}",
                "/offer/cancel/{userId}/{offerId}"
            )
    }
}
