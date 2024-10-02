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
    var offer : UserOffer? = builder.offer  // debe tener al usuario que creo la oferta

    @ManyToOne
    @JoinColumn(name = "user_id")
    var bidderUser : User? = builder.bidderUser

    @Column
    var startTime : Date = builder.startTime
    /*
    @Column
    var status : TransactionStatus = builder.status   // Necesario ??  calculo que si para poder avanzar en las etapas de la transaction
    */

    class TransactionBuilder {
        var offer: UserOffer? = null
            private set
        var bidderUser: User? = null
            private set
        var startTime: Date = Date()
            private set

        fun offer(offer: UserOffer) = apply {
            //  comprobar que la oferta este abierta (no iniciada, cerrada)
            //require(isAOpenOffer(offer)) { "the offer is not open." }
            this.offer = offer
        }

        fun bidderUser(bidderUser: User) = apply {
            require(isADiferentUser(bidderUser)) { "A single user can not bid on their own offer." }
            this.bidderUser = bidderUser
        }

        private fun isADiferentUser(bidderUser: User): Boolean {
            return this.offer!!.user() == bidderUser
            //return bidderUser.name != offer?.userName  &&  bidderUser.lastName != offer?.userLastName // mirar seguro da un error porque pide offer
            // return bidderUser.email != offer.user.email   // lo que es unico entre usuario es el email  --> implementar equals en User
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