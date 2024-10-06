package com.example.demo.model

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "transaction_table")
class Transaction(builder: TransactionBuilder) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null  // Borrar ? (esta para que compile)

    @ManyToOne
    @JoinColumn(name = "offer_id")
    var offer : UserOffer? = builder.offer

    @ManyToOne
    @JoinColumn(name = "user_id")
    var acceptingUser : User? = builder.acceptingUser

    @Column
    var startTime : Date? = builder.startTime

    @Column
    var transactionStatus : TransactionStatus? = builder.transactionStatus


    class TransactionBuilder {
        var offer: UserOffer? = null
            private set
        var acceptingUser: User? = null
            private set
        var startTime: Date? = null
            private set
        var transactionStatus: TransactionStatus? = null
            private set

        fun offer(offer: UserOffer) = apply {
            require(isAAvailableOffer(offer)) { "The offer is unavailable." }
            this.offer = offer
        }

        fun acceptingUser(acceptingUser: User) = apply {
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

        private fun isADiferentUser(acceptingUser: User): Boolean {
            return this.offer!!.user() != acceptingUser
        }

        private fun isAAvailableOffer(offer: UserOffer): Boolean {
            return offer.isAvailable()
        }


    }

    /*
    fun cancel(){
        // cambiar el estadod de la oferta a disponible (Abierto/Open)
        // guardar la Transaction en a la persona que cancelo la Transaction
        // restar el puntaje en la reputacion a la persona que cancelo la Transaction
        // dar un mensaje que se cancelo exitosamente la Transaction (servicio)
    }
    */

}