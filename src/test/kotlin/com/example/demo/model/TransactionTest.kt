package com.example.demo.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Date

class TransactionTest {


    @Test
    fun `should create a Transaction with a available offer`() {
        val offer = UserOffer.UserOfferBuilder().offerStatus(OfferStatus.AVAILABLE).build()
        val transaction = Transaction.TransactionBuilder().offer(offer).build()

        assertEquals(offer, transaction.offer)
    }

    @Test
    fun `should throw exception for unvailable offer`() {
        val offer = UserOffer.UserOfferBuilder().offerStatus(OfferStatus.UNVAILABLE).build()
        val exception = assertThrows<IllegalArgumentException> {
            Transaction.TransactionBuilder().offer(offer).build()
        }
        assertEquals("The offer is unavailable.", exception.message)
    }

    /*  Para identificar diferentes user usar mail ?
    @Test
    fun `should create a transaction with a differnt users`() {
        val user = User.UserBuilder().id(1).build()
        val acceptingUser = User.UserBuilder().id(2).build()

        val offer = UserOffer.UserOfferBuilder().user(user).build()
        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .build()

        assertEquals(offer, transaction.offer)
    }

    @Test
    fun `should throw exception for transaction with a same user`() {
        val user = User.UserBuilder().id(1).build()
        val offer = UserOffer.UserOfferBuilder().user(user).build()

        val exception = assertThrows<IllegalArgumentException> {
            Transaction.TransactionBuilder()
                .offer(offer)
                .acceptingUser(user)
                .build()
        }
        assertEquals("A single user can not bid on their own offer.", exception.message)
    }
*/

    @Test
    fun `should create a transaction with a date`() {
        val transaction = Transaction.TransactionBuilder().startTime(Date())

        assertEquals(Date(), transaction.startTime)
    }






}