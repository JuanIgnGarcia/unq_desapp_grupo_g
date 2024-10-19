package com.example.demo.model

import com.example.demo.exceptions.UserOfferException
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
    var cryptoMounts: Double? = builder.cryptoMounts
    @Column
    var cryptoPrice: Double? = builder.cryptoPrice
    @Column
    var argsMounts: Double? = builder.argsMounts
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User<Any?>? = builder.user
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
        var user: User<Any?>? = null
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
            this.cryptoPrice = cryptoPrice
        }

        fun argsMounts(argsMounts: Double) = apply {
            this.argsMounts = argsMounts
        }

        fun user(user: User<Any?>) = apply {
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



    }

    fun userName() : String {  return this.user!!.userName() }

    fun userLastName(): String { return this.user!!.userLastName() }

    fun user(): User<Any?> { return this.user!! }

    fun isAvailable(): Boolean {return this.offerStatus!!.isAvailable()}

    fun invalidate() {this.offerStatus = OfferStatus.UNVAILABLE}

    fun isABuy(): Boolean { return this.offerType!!.isABuy()}


    fun finishSuccessfullyOffer(transactionDuration: Long) {
        this.offerStatus = OfferStatus.UNVAILABLE
        this.user!!.userUpdateForFinishTransaction(transactionDuration)
    }

    fun isASell(): Boolean { return !isABuy()}

    fun avalidate() {this.offerStatus = OfferStatus.AVAILABLE}

    fun validateCancelTheOffer(userId: String) {
        if (this.offerStatus == OfferStatus.UNVAILABLE ||
            this.user!!.id == userId.toLong()                 // Cambiar ?
        ){
            throw UserOfferException("it cannot validate the cancellation of the offer")
        }
    }

    fun totalAmount() : Double {return this.cryptoMounts!! * this.cryptoPrice!! }

    fun argsMounts() : Double {return this.argsMounts!! }

}
