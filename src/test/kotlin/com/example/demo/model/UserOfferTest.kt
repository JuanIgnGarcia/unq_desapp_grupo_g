package com.example.demo.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertNotNull


class UserOfferTest {

    @Test
    fun `I create an offer user but I do not specify the crypto`() {
        val userOffer = UserOffer.UserOfferBuilder()
        assertNotNull(userOffer)
    }

    @Test
    fun `I create a useroffer with the crypto to publish`() {
        val userOffer = UserOffer.UserOfferBuilder()
        userOffer.cryptoSymbol("AUDIOUSDT")
        assertEquals("AUDIOUSDT",userOffer.cryptoSymbol)
    }

    @Test
    fun `I create a useroffer and the nominal value is correct`() {
        val userOffer = UserOffer.UserOfferBuilder()
        //userOffer.cryptoMounts(3.343323)

    }

    @Test
    fun `I created a useroffer and the crypto price is correct`() {
        val userOffer = UserOffer.UserOfferBuilder()
        userOffer.cryptoSymbol("AUDIOUSDT")
        userOffer.cryptoPrice(0.1340)
        assertNotNull(userOffer.cryptoPrice)
    }

    @Test
    fun `I created a useroffer and the crypto price not is correct`() {
        val userOffer = UserOffer.UserOfferBuilder()
        val exception = assertThrows<IllegalArgumentException> {
            userOffer.cryptoSymbol("AUDIOUSDT")
            userOffer.cryptoPrice(2.34452)
        }
        assertEquals("The crypto price not valid.", exception.message)
    }





}