package com.example.demo.model

import com.example.demo.exceptions.TransactionException
import com.example.demo.exceptions.UserException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Date
import io.mockk.*

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


    @Test
    fun `should make transfer successfully`() {
        val offer = mockk<UserOffer>()
        val acceptingUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.user() } returns mockk()
        every { offer.isABuy() } returns true
        every { offer.finishSuccessfullyOffer(any()) } just Runs

        every { acceptingUser.userUpdateForFinishTransaction(any()) } just Runs
        every { acceptingUser == any() } returns true

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.ACTIVE)
            .build()

        transaction.makeTransfer(acceptingUser)


        verify { offer.finishSuccessfullyOffer(any()) }
        assertEquals(TransactionStatus.CLOSE, transaction.transactionStatus)
    }

    @Test
    fun `should not make transfer successfully by external user `() {
        val offer = mockk<UserOffer>()
        val acceptingUser = mockk<User<Any?>>(relaxed = true)
        val anotherUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.user() } returns mockk()
        every { offer.isABuy() } returns true
        every { offer.finishSuccessfullyOffer(any()) } just Runs

        every { acceptingUser.userUpdateForFinishTransaction(any()) } just Runs
        every { acceptingUser == anotherUser } returns false

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.ACTIVE)
            .build()

        assertThrows<TransactionException> {
            transaction.makeTransfer(anotherUser)
        }

    }

    @Test
    fun `should not make transfer successfully to the status Close`() {
        val offer = mockk<UserOffer>()
        val acceptingUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.user() } returns mockk()
        every { offer.isABuy() } returns true
        every { offer.finishSuccessfullyOffer(any()) } just Runs

        every { acceptingUser.userUpdateForFinishTransaction(any()) } just Runs
        every { acceptingUser == any() } returns true

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.CLOSE)
            .build()

        assertThrows<TransactionException> {
            transaction.makeTransfer(acceptingUser)
        }

    }

    @Test
    fun `should not make transfer successfully to the status because is not a buy`() {
        val offer = mockk<UserOffer>()
        val acceptingUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.user() } returns mockk()
        every { offer.isABuy() } returns false
        every { offer.finishSuccessfullyOffer(any()) } just Runs

        every { acceptingUser.userUpdateForFinishTransaction(any()) } just Runs
        every { acceptingUser == any() } returns true

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.ACTIVE)
            .build()

        assertThrows<TransactionException> {
            transaction.makeTransfer(acceptingUser)
        }
    }

    @Test
    fun `should confirm receipt successfully`() {
        val offer = mockk<UserOffer>()
        val acceptingUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.user() } returns mockk()
        every { offer.isASell() } returns true
        every { offer.finishSuccessfullyOffer(any()) } just Runs

        every { acceptingUser.userUpdateForFinishTransaction(any()) } just Runs
        every { acceptingUser == any() } returns true

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.ACTIVE)
            .build()

        transaction.confirmReceipt(acceptingUser)


        verify { offer.finishSuccessfullyOffer(any()) }
        verify { acceptingUser.userUpdateForFinishTransaction(any())}
        assertEquals(TransactionStatus.CLOSE, transaction.transactionStatus)
    }

    @Test
    fun `should not confirm receipt successfully by external user `() {
        val offer = mockk<UserOffer>()
        val acceptingUser = mockk<User<Any?>>(relaxed = true)
        val anotherUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.user() } returns mockk()
        every { offer.isASell() } returns false
        every { offer.finishSuccessfullyOffer(any()) } just Runs

        every { acceptingUser.userUpdateForFinishTransaction(any()) } just Runs
        every { acceptingUser == anotherUser } returns false

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.ACTIVE)
            .build()

        assertThrows<TransactionException> {
            transaction.confirmReceipt(acceptingUser)
        }

    }

    @Test
    fun `should not confirm receipt successfully to the status because is not a sell`() {
        val offer = mockk<UserOffer>()
        val acceptingUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.user() } returns mockk()
        every { offer.isASell() } returns false
        every { offer.finishSuccessfullyOffer(any()) } just Runs

        every { acceptingUser.userUpdateForFinishTransaction(any()) } just Runs
        every { acceptingUser == any() } returns true

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.ACTIVE)
            .build()


        assertThrows<TransactionException> {
            transaction.confirmReceipt(acceptingUser)
        }
    }

    @Test
    fun `should not confirm receipt successfully to the status Close`() {
        val offer = mockk<UserOffer>()
        val acceptingUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.user() } returns mockk()
        every { offer.isASell() } returns true
        every { offer.finishSuccessfullyOffer(any()) } just Runs

        every { acceptingUser.userUpdateForFinishTransaction(any()) } just Runs
        every { acceptingUser == any() } returns true

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.CLOSE)
            .build()

        assertThrows<TransactionException> {
            transaction.confirmReceipt(acceptingUser)
        }

    }

    @Test
    fun `should cancel successfully by the acceptingUser`() {
        val offer = mockk<UserOffer>()
        val acceptingUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.avalidate() } just Runs
        every { offer.user() } returns mockk()

        every { acceptingUser != any() } returns true

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.ACTIVE)
            .build()

        transaction.cancelTransaction(acceptingUser)


        verify { offer.avalidate() }
        verify { acceptingUser.userUpdateForCancelTransaction() }

        assertEquals(TransactionStatus.CANCEL, transaction.transactionStatus)
    }

    @Test
    fun `should cancel successfully by the offer user`() {
        val offer = mockk<UserOffer>()
        val user = mockk<User<Any?>>(relaxed = true)
        val acceptingUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.avalidate() } just Runs
        every { offer.user() } returns user

        every { user == offer!!.user() } returns true
        every { user != acceptingUser } returns true

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.ACTIVE)
            .build()

        transaction.cancelTransaction(user)


        verify { offer.avalidate() }
        verify { user.userUpdateForCancelTransaction()}

        assertEquals(TransactionStatus.CANCEL, transaction.transactionStatus)
    }

    @Test
    fun `should not cancel  by the external user`() {
        val offer = mockk<UserOffer>()
        val acceptingUser = mockk<User<Any?>>(relaxed = true)
        val anotherUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.avalidate() } just Runs
        every { offer.user() } returns mockk()

        every { anotherUser != any() } returns false

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.ACTIVE)
            .build()

        assertThrows<TransactionException> {
            transaction.cancelTransaction(anotherUser)
        }

    }

    @Test
    fun `should give the total amount`() {
        val totalAmount = 10.00

        val offer = mockk<UserOffer>()
        val acceptingUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.totalAmount() } returns totalAmount
        every { offer.avalidate() } just Runs
        every { offer.user() } returns mockk()

        every { acceptingUser != any() } returns true

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.ACTIVE)
            .build()

        assertEquals(transaction.totalAmount(), totalAmount)
    }

    @Test
    fun `should give the total amount ARG`() {
        val totalAmountARG = 10.00

        val offer = mockk<UserOffer>()
        val acceptingUser = mockk<User<Any?>>(relaxed = true)

        every { offer.isAvailable() } returns true
        every { offer.argsMounts() } returns totalAmountARG
        every { offer.avalidate() } just Runs
        every { offer.user() } returns mockk()

        every { acceptingUser != any() } returns true

        val transaction = Transaction.TransactionBuilder()
            .offer(offer)
            .acceptingUser(acceptingUser)
            .startTime(Date())
            .transactionStatus(TransactionStatus.ACTIVE)
            .build()

        assertEquals(transaction.totalAmountArgs(), totalAmountARG)
    }



}