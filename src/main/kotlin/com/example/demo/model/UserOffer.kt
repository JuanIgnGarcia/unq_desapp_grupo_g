package com.example.demo.model

import com.example.demo.service.CryptoService
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
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = builder.user
    @Column
    var offerDate: Date? = builder.offerDate
    @Column
    var offerType: OfferType? = builder.offerType
    @Column
    var offerStatus : OfferStatus? = builder.offerStatus

    class UserOfferBuilder {
        var cryptoSymbol: String? = null
            private set
        var cryptoMounts: Double? = null
            private set
        var cryptoPrice: Double? = null
            private set
        var argsMounts: Double? = null
            private set
        var user: User? = null
            private set
        var offerDate: Date? = null
            private set
        var offerType: OfferType? = null
            private set
        var offerStatus: OfferStatus? = null
            private set


        fun cryptoSymbol(cryptoSymbol: String) = apply {
            CryptoSymbolHelper.validateCriptoSymbol(cryptoSymbol)
            this.cryptoSymbol = cryptoSymbol
        }

        fun cryptoMounts(cryptoMounts: Double) = apply {
            require(cryptoMounts >= 0) { "The crypto mounts cannot be negative." }
            this.cryptoMounts = cryptoMounts
        }

        fun cryptoPrice(cryptoPrice: Double) = apply {
            require(isValidCryptoPrice(cryptoPrice)) { "The crypto price not valid." }
            this.cryptoPrice = cryptoPrice
        }

        fun argsMounts(argsMounts: Double) = apply {
            this.argsMounts = argsMounts
        }

        fun user(user: User) = apply {
            this.user = user
        }

        fun offerDate(offerDate: Date) = apply {
            this.offerDate = offerDate
        }

        fun offerType(offerType: OfferType) = apply {
            this.offerType = offerType
        }

        fun offerStatus(offerStatus: OfferStatus) = apply {
            this.offerStatus = offerStatus
        }

        fun build(): UserOffer {
            return UserOffer(this)
        }

        private fun percentageDifference(price1 : Double, price2 : Double) : Boolean {
            val difference  = abs(price1 - price2)
            val allowedMargin = price1 * 0.05
            return difference <= allowedMargin
        }

        private fun isValidCryptoPrice(cryptoPrice: Double): Boolean {
            val lastCryptoValue = CryptoService().getCrypto(cryptoSymbol!!).price.toDouble()
            return percentageDifference(cryptoPrice,lastCryptoValue)
        }
    }

    fun userName() : String {  return this.user!!.userName() }

    fun userLastName(): String { return this.user!!.userLastName() }

    fun user(): User? { return this.user }

}
