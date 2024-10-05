package com.example.demo.model


//import com.example.demo.exceptions.CryptoNotFoundException
import com.example.demo.service.Proxys.ProxyBinance
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class UserOfferTest {


    @Test
    fun `I create a useroffer with the crypto to publish`() {
        val userOffer = UserOffer.UserOfferBuilder()
        userOffer.cryptoSymbol("AUDIOUSDT")
        assertEquals("AUDIOUSDT",userOffer.cryptoSymbol)
    }
/*
    @Test
    fun `should throw exception for invalid crypto symbol `() {
        val exception = assertThrows<CryptoNotFoundException> {  // CryptoNotFoundException
            UserOffer.UserOfferBuilder().cryptoSymbol("EE")
        }
        assertEquals("Crypto symbol EE is not valid.", exception.message)
    }
*/
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

    /*  funciona pero mejorar
    @Test
    fun `should create a user offer with a cryptoPrice in range`() {
        val userOffer = UserOffer.UserOfferBuilder()
        val cryptoPriceActual = ProxyBinance().cryptosPrices(arrayOf("AUDIOUSDT").toList()).first().price.toDouble()
        userOffer.cryptoSymbol("AUDIOUSDT")
        userOffer.cryptoPrice(cryptoPriceActual)
         assertEquals(userOffer.cryptoPrice,cryptoPriceActual)
    }

    @Test
    fun `should throw exception for cryptoPrice is under the range`() {
        val exception = assertThrows<IllegalArgumentException> {
            val userOffer = UserOffer.UserOfferBuilder()

            var cryptoPriceActual = ProxyBinance().cryptosPrices(arrayOf("AUDIOUSDT").toList()).first().price.toDouble()
            cryptoPriceActual -= (cryptoPriceActual * 0.06)

            userOffer.cryptoSymbol("AUDIOUSDT")

            userOffer.cryptoPrice(cryptoPriceActual)
        }
        assertEquals("The crypto price not valid.", exception.message)
    }

    @Test
    fun `should throw exception for cryptoPrice is over the range`() {
        val exception = assertThrows<IllegalArgumentException> {
            val userOffer = UserOffer.UserOfferBuilder()

            var cryptoPriceActual = ProxyBinance().cryptosPrices(arrayOf("AUDIOUSDT").toList()).first().price.toDouble()
            cryptoPriceActual += (cryptoPriceActual * 0.06)

            userOffer.cryptoSymbol("AUDIOUSDT")

            userOffer.cryptoPrice(cryptoPriceActual)
        }
        assertEquals("The crypto price not valid.", exception.message)
    }

    */





}