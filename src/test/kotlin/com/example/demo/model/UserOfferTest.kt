package com.example.demo.model

import com.example.demo.exceptions.CryptoNotFoundException
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserOfferTest {


    @Test
    fun `I create a useroffer with the crypto to publish`() {
        val userOffer = UserOffer.UserOfferBuilder()
        userOffer.cryptoSymbol("AUDIOUSDT")
        assertEquals("AUDIOUSDT",userOffer.cryptoSymbol)
    }

    @Test
    fun `should throw exception for invalid crypto symbol `() {
        val exception = assertThrows<CryptoNotFoundException> {  // CryptoNotFoundException
            UserOffer.UserOfferBuilder().cryptoSymbol("EE")
        }
        assertEquals("Crypto symbol EE is not valid.", exception.message)
    }

    @Test
    fun `should create a user offer with a positive crypto mounts`() {
        val userOffer = UserOffer.UserOfferBuilder().cryptoMounts(3.00)
        assertEquals(userOffer.cryptoMounts,3.00)
    }

    @Test
    fun `should throw exception for negative crypto mounts`() {
        val exception = assertThrows<IllegalArgumentException> {
            UserOffer.UserOfferBuilder().cryptoMounts(-1.00)
        }
        assertEquals("The crypto mounts cannot be negative.", exception.message)
    }

    @Test
    fun `should give the user name`() {
        val userM = mockk<User<Any?>>(relaxed = true)
        every { userM.userName() } returns ("Mariana")
        val userOffer = UserOffer.UserOfferBuilder().user(userM).build()

        assertEquals(userOffer.userName(),"Mariana")
    }

    @Test
    fun `should throw exception for null user name`() {
        val user = User.UserBuilder().build()
        val userOffer = UserOffer.UserOfferBuilder().user(user).build()

        assertThrows<NullPointerException> {
            userOffer.userName()
        }
    }

    @Test
    fun `should give the user last name`() {
        val userM = mockk<User<Any?>>(relaxed = true)
        every { userM.userLastName() } returns ("Lopez")
        val userOffer = UserOffer.UserOfferBuilder().user(userM).build()

        assertEquals(userOffer.userLastName(),"Lopez")
    }

    @Test
    fun `should throw exception for null user last name`() {
        val user = User.UserBuilder().build()
        val userOffer = UserOffer.UserOfferBuilder().user(user).build()

        assertThrows<NullPointerException> {
            userOffer.userLastName()
        }
    }

    @Test
    fun `should give the user`() {
        val userM = mockk<User<Any?>>(relaxed = true)
        every { userM.userLastName() } returns ("Lopez")

        val userOffer = UserOffer.UserOfferBuilder().user(userM).build()

        assertEquals(userOffer.user(),userM)
        assertEquals(userOffer.user().userLastName(),"Lopez")
    }

    @Test
    fun `should throw exception for null user`() {
        val userOffer = UserOffer.UserOfferBuilder().build()

        assertThrows<NullPointerException> {
            userOffer.user()
        }
    }

    @Test
    fun `should be available`() {
        val userOffer = UserOffer.UserOfferBuilder().offerStatus(OfferStatus.AVAILABLE).build()

        assertTrue(userOffer.isAvailable())
    }

    @Test
    fun `should be not available`() {
        val userOffer = UserOffer.UserOfferBuilder().offerStatus(OfferStatus.UNVAILABLE).build()

        assertFalse(userOffer.isAvailable())
    }

    @Test
    fun `should invalidate a offer`() {
        val userOffer = UserOffer.UserOfferBuilder().offerStatus(OfferStatus.AVAILABLE).build()

        userOffer.invalidate()

        assertEquals(userOffer.offerStatus,OfferStatus.UNVAILABLE)
    }

    @Test
    fun `should be a buy`() {
        val userOffer = UserOffer.UserOfferBuilder().offerType(OfferType.BUY).build()

        assertTrue(userOffer.isABuy())
    }

    @Test
    fun `should be not a buy`() {
        val userOffer = UserOffer.UserOfferBuilder().offerType(OfferType.SELL).build()

        assertFalse(userOffer.isABuy())
    }


    @Test
    fun `should finish the offer succesfully`() {
        val userM = mockk<User<Any?>>(relaxed = true)
        val offer = UserOffer.UserOfferBuilder()
                .user(userM)
                .offerType(OfferType.BUY)
                .offerStatus(OfferStatus.AVAILABLE)
                .build()

        every { userM.point } returns 0
        every { userM.mountCompletedTransactions } returns 0
        every { userM.userUpdateForFinishTransaction(any()) } just Runs
        every { userM == any() } returns true



        offer.finishSuccessfullyOffer(5)


        verify { userM.userUpdateForFinishTransaction(any()) }
        assertEquals(OfferStatus.UNVAILABLE, offer.offerStatus)
    }

    @Test
    fun `should return the cryptoSymbol`() {
        val userOffer = UserOffer.UserOfferBuilder().cryptoSymbol("AUDIOUSDT").build()

        assertEquals(userOffer.cryptoSymbol(),"AUDIOUSDT")
    }

    @Test
    fun `should return the cryptoPrice`() {
        val userOffer = UserOffer.UserOfferBuilder().cryptoPrice(1.0).build()

        assertEquals(userOffer.cryptoPrice(),1.0)
    }



}