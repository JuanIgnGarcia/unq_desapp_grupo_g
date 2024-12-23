package com.example.demo.model

import com.example.demo.exceptions.TransactionException
import jakarta.persistence.*
import java.util.*
import java.util.concurrent.TimeUnit

@Entity
@Table(name = "transaction_table")
class Transaction(builder: TransactionBuilder) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "offer_id")
    var offer : UserOffer? = builder.offer

    @ManyToOne
    @JoinColumn(name = "user_id")
    var acceptingUser : User<Any?>? = builder.acceptingUser

    @Column
    var startTime : Date? = builder.startTime

    @Column
    var transactionStatus : TransactionStatus? = builder.transactionStatus


    class TransactionBuilder {
        var offer: UserOffer? = null
            private set
        var acceptingUser: User<Any?>? = null
            private set
        var startTime: Date? = null
            private set
        var transactionStatus: TransactionStatus? = null
            private set

        fun offer(offer: UserOffer) = apply {
            require(isAAvailableOffer(offer)) { "The offer is unavailable." }
            this.offer = offer
        }

        fun acceptingUser(acceptingUser: User<Any?>) = apply {
            require(isADiferentUser(acceptingUser)) { "A single user can not bid on their own offer." }
            this.acceptingUser = acceptingUser
        }

        fun startTime(startTime: Date) = apply {
            this.startTime = startTime
        }

        fun transactionStatus(transactionStatus: TransactionStatus) = apply {
            this.transactionStatus = transactionStatus
        }

        fun build(): Transaction {
            return Transaction(this)
        }

        private fun isADiferentUser(acceptingUser: User<Any?>): Boolean {
            return this.offer!!.user() != acceptingUser
        }

        private fun isAAvailableOffer(offer: UserOffer): Boolean {
            return offer.isAvailable()
        }

    }

    fun makeTransfer(user: User<Any?>) {
        val finishTime = Date()

        validateTransaction(user)
        val transactionDuration = minutesElapsed(this.startTime!!,finishTime)

        updateOffer(transactionDuration)
        this.acceptingUser!!.userUpdateForFinishTransaction(transactionDuration)
        this.transactionStatus = TransactionStatus.CLOSE
    }

    fun confirmReceipt(user: User<Any?>) {
        val finishTime = Date() // "+ ventaja a los usuarios para mejor experiencia"

        validateReceipt(user)
        val transactionDuration = minutesElapsed(this.startTime!!,finishTime)

        updateOffer(transactionDuration)
        this.acceptingUser!!.userUpdateForFinishTransaction(transactionDuration)
        this.transactionStatus = TransactionStatus.CLOSE
    }

    fun cancelTransaction(user: User<Any?>) {
        validateUsers(user) // Falta validar transactionStatus deberia estar ACTIVE
        this.transactionStatus = TransactionStatus.CANCEL
        this.offer!!.avalidate()
        user.userUpdateForCancelTransaction()
    }

    fun totalAmount() : Double  {
        return this.offer!!.totalAmount()
    }

    fun totalAmountArgs() : Double{
        return this.offer!!.argsMounts()
    }

    private fun validateUsers(user: User<Any?>) {
        if( user != this.acceptingUser &&
            user != this.offer!!.user() ){
            throw TransactionException("The user ${user.id} doesn't participe in that transaction")
        }
    }

    private fun minutesElapsed(startTime: Date, finishTime: Date): Long {
        val durationMiliSeconds = finishTime.time - startTime.time
        return TimeUnit.MILLISECONDS.toMinutes(durationMiliSeconds)
    }

    private fun updateOffer(transactionDuration: Long) {
        this.offer!!.finishSuccessfullyOffer(transactionDuration)
    }

    private fun validateTransaction(user: User<Any?>) {
        if (!validateUser(user) ||
            !validateTransactionStatus() ||
            !this.offer!!.isABuy()
           ){
            throw TransactionException("Transaction doesnt satify some condition")
        }
    }

    private fun validateReceipt(user: User<Any?>) {
        if (!validateUser(user) ||
            !validateTransactionStatus() ||
            !this.offer!!.isASell()
        ){
            throw TransactionException("Transaction doesnt satify some condition")
        }
    }

    private fun validateTransactionStatus(): Boolean {
        return this.transactionStatus!!.isActive()
    }

    private fun validateUser(user: User<Any?>): Boolean {
        return user == this.offer!!.user()
    }

    fun cryptoSymbol(): String {
        return offer!!.cryptoSymbol()
    }

    fun cryptoPrice(): Double {
        return offer!!.cryptoPrice()
    }


}