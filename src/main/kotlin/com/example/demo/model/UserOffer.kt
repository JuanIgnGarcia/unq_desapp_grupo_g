package com.example.demo.model

import com.example.demo.service.CryptoService
import com.example.demo.service.Proxys.ProxyUsdPrice
import jakarta.persistence.*
import java.util.Date
import kotlin.math.abs

@Entity
@Table(name = "userOffer_table")
class UserOffer private constructor(builder: UserOfferBuilder) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @Column
    var cryptoSymbol: String? = builder.cryptoSymbol
    @Column
    var cryptoMounts: Double? = builder.cryptoMounts
    @Column
    var cryptoPrice: Double? = builder.cryptoPrice
    @Column
    var argsMounts: Double? = builder.argsMounts
    @Column
    var userName: String? = builder.userName
    @Column
    var userLastName: String? = builder.userLastName
    @Column
    var offerDate: Date? = builder.offerDate
    @Column
    var offerType: OfferType? = builder.offerType

    class UserOfferBuilder {
        var cryptoSymbol: String? = null
            private set
        var cryptoMounts: Double? = null
            private set
        var cryptoPrice: Double? = null
            private set
        var argsMounts: Double? = null
            private set
        var userName: String? = null
            private set
        var userLastName: String? = null
            private set
        var offerDate: Date? = null
            private set
        var offerType: OfferType? = null
            private set

        fun cryptoSymbol(cryptoSymbol: String) = apply {
            CryptoSymbolHelper.validateCriptoSymbol(cryptoSymbol)
            this.cryptoSymbol = cryptoSymbol
        }

        fun cryptoMounts(cryptoMounts: Double) = apply {
            this.cryptoMounts = cryptoMounts
        }

        fun cryptoPrice(cryptoPrice: Double) = apply {
            require(isValidCryptoPrice(cryptoPrice)) { "The crypto price not valid." }
            this.cryptoPrice = cryptoPrice
        }

        fun argsMounts(argsMounts: Double) = apply {
            this.argsMounts = argsMounts
        }

        fun userName(userName: String) = apply {
            this.userName = userName
        }

        fun userLastName(userLastName: String) = apply {
            this.userLastName = userLastName
        }

        fun offerDate(offerDate: Date) = apply {
            this.offerDate = offerDate
        }

        fun offerType(offerType: OfferType) = apply {
            this.offerType = offerType
        }

        fun build(): UserOffer {
            return UserOffer(this)
        }

        private fun percentageDifference(p1 : Double, p2 : Double) : Double {
            val diferencia = abs(p1 - p2)
            val promedio = (p1 + p2) / 2
            return (diferencia / promedio) * 100
        }
        private fun isValidCryptoPrice(cryptoPrice: Double): Boolean {
            val lastCryptoValue = CryptoService().getCrypto(cryptoSymbol!!).price.toDouble()
            return percentageDifference(cryptoPrice, lastCryptoValue) <= 5

        }

    }

}
