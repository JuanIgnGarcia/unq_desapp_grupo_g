package com.example.demo.model

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "userOffer_table")
class UserOffer private constructor(builder: UserOfferBuilder) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @Column
    var cryptoSymbol: String? = builder.cryptoSymbol
    @Column
    var cryptoMounts: Float? = builder.cryptoMounts
    @Column
    var cryptoPrice: Float? = builder.cryptoPrice
    @Column
    var argsMounts: Float? = builder.argsMounts
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
        var cryptoMounts: Float? = null
            private set
        var cryptoPrice: Float? = null
            private set
        var argsMounts: Float? = null
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

        fun cryptoMounts(cryptoMounts: Float) = apply {
            this.cryptoMounts = cryptoMounts
        }

        fun cryptoPrice(cryptoPrice: Float) = apply {
            require(isValidCryptoPrice(cryptoPrice)) { "The crypto price not valid." }
            this.cryptoPrice = cryptoPrice
        }

        fun argsMounts(argsMounts: Float) = apply {
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

        private fun isValidCryptoPrice(cryptoPrice: Float?): Boolean {
            return true // Buscarla en cache
        }

    }

}
