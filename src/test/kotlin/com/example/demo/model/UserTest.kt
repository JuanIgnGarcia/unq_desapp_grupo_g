package com.example.demo.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.concurrent.TimeoutException
import kotlin.test.assertNotNull


class UserTest {

    @Test
    fun `should create a user with a valid name`() {
        val user = User.UserBuilder().name("Marcos")

        assertNotNull(user)
        assertEquals("Marcos", user.name)
    }

    @Test
    fun `should throw exception for short name`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().name("Ma")
        }
        assertEquals("The name must be between 3 and 30 characters long.", exception.message)
    }

    @Test
    fun `should throw exception for long name`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().name("Maaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        }
        assertEquals("The name must be between 3 and 30 characters long.", exception.message)
    }

    @Test
    fun `should create a user with a valid lastName`() {
        val user = User.UserBuilder().lastName("Dias")

        assertNotNull(user)
        assertEquals("Dias", user.lastName)
    }

    @Test
    fun `should throw exception for short lastName`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().lastName("D")
        }
        assertEquals("The last name must be between 3 and 30 characters long.", exception.message)
    }

    @Test
    fun `should throw exception for long lastName`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().lastName("Diasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
        }
        assertEquals("The last name must be between 3 and 30 characters long.", exception.message)
    }

    @Test
    fun `should create a user with a valid email`() {
        val user = User.UserBuilder().email("Marcos.dias@example.com")

        assertNotNull(user)
        assertEquals("Marcos.dias@example.com", user.email)
    }

    @Test
    fun `should throw exception for email without a at sign `() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().email("MARCOSDIASEXAMPLE.com")
        }
        assertEquals("The email format is not valid.", exception.message)
    }

    @Test
    fun `should throw exception for email with space `() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().email("MARCOS DIASEXAMPLE.COM")
        }
        assertEquals("The email format is not valid.", exception.message)
    }

    @Test
    fun `should create a user with a valid address`() {
        val user = User.UserBuilder().address("123 Main Street")

        assertNotNull(user)
        assertEquals("123 Main Street", user.address)
    }

    @Test
    fun `should throw exception for a short address`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().address("123")
        }
        assertEquals("The address must be between 10 and 30 characters long.", exception.message)
    }

    @Test
    fun `should throw exception for a long address`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().address("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111")
        }
        assertEquals("The address must be between 10 and 30 characters long.", exception.message)
    }

    @Test
    fun `should create a user with a valid pasword`() {
        val user = User.UserBuilder().password("StrongPassword1!")

        assertNotNull(user)
        assertEquals("StrongPassword1!", user.password)
    }

    @Test
    fun `should throw exception for a password without a uppercase`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().password("strongpassword1!")
        }
        assertEquals("The password must have at least 1 lowercase letter, 1 uppercase letter, 1 special character, and be at least 6 characters long.", exception.message)
    }

    @Test
    fun `should throw exception for a password without a lowercase`() {
        val exception = assertThrows<IllegalArgumentException> {
            val pass = "strongpassword1!".uppercase()
            User.UserBuilder().password(pass)
        }
        assertEquals("The password must have at least 1 lowercase letter, 1 uppercase letter, 1 special character, and be at least 6 characters long.", exception.message)
    }

    @Test
    fun `should throw exception for a password without a special character`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().password("strongpassword1")
        }
        assertEquals("The password must have at least 1 lowercase letter, 1 uppercase letter, 1 special character, and be at least 6 characters long.", exception.message)
    }

    @Test
    fun `should throw exception for a short password `() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().password("sT!")
        }
        assertEquals("The password must have at least 1 lowercase letter, 1 uppercase letter, 1 special character, and be at least 6 characters long.", exception.message)
    }

    @Test
    fun `should create a user with a valid cvu MercadoPago`() {
        val user = User.UserBuilder().cvuMercadoPago("1234567890123456789012")

        assertNotNull(user)
        assertEquals("1234567890123456789012", user.cvuMercadoPago)
    }

    @Test
    fun `should create a user with a long cvu MercadoPago`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().cvuMercadoPago("12345678901234567890123")
        }
        assertEquals("The MercadoPago CVU must be 22 digits long.", exception.message)
    }

    @Test
    fun `should create a user with a short cvu MercadoPago`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().cvuMercadoPago("123456789012345678901")
        }
        assertEquals("The MercadoPago CVU must be 22 digits long.", exception.message)
    }

    @Test
    fun `should create a user with a valid crypto Address`() {
        val user = User.UserBuilder().cryptoAddress("12345678")

        assertNotNull(user)
        assertEquals("12345678", user.cryptoAddress)
    }

    @Test
    fun `should create a user with a long crypto Address`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().cryptoAddress("123456789")
        }
        assertEquals("The crypto wallet address must be 8 digits long.", exception.message)
    }

    @Test
    fun `should throw exception for try to create a user with a short crypto Address`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().cryptoAddress("1234567")
        }
        assertEquals("The crypto wallet address must be 8 digits long.", exception.message)
    }

    @Test
    fun `should create a user with positive points`() {
        val user = User.UserBuilder().point(1)

        assertNotNull(user)
        assertEquals(1, user.point)
    }

    @Test
    fun `should throw exception for try to create a user with negative points`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().point(-1)
        }
        assertEquals("Points cannot be negative.", exception.message)
    }

    @Test
    fun `should create a user with positive mountCompletedTransactions`() {
        val user = User.UserBuilder().mountCompletedTransactions(1)

        assertNotNull(user)
        assertEquals(1, user.mountCompletedTransactions)
    }

    @Test
    fun `should create a user with negative mountCompletedTransactions`() {
        val exception = assertThrows<IllegalArgumentException> {
            User.UserBuilder().mountCompletedTransactions(-1)
        }
        assertEquals("Completed transactions cannot be negative.", exception.message)
    }

    @Test
    fun `should give user name`() {
        val user = User.UserBuilder().name("Mariana").build()

        assertEquals("Mariana", user.userName())
    }

    @Test
    fun `should throw exception for null name`() {
        val user = User.UserBuilder().build()

        assertThrows<NullPointerException> {
            user.userName()
        }
    }

    @Test
    fun `should give user lastname`() {
        val user = User.UserBuilder().lastName("Lopez").build()

        assertEquals("Lopez", user.userLastName())
    }

    @Test
    fun `should throw exception for null lastname`() {
        val user = User.UserBuilder().build()

        assertThrows<NullPointerException> {
            user.userLastName()
        }
    }

    /*  Como testear == ?
    @Test
    fun `should be the same user`() {
        val user = User.UserBuilder().id(1).build()
        assertTrue(user == user)
        }
    }

    @Test
    fun `should not be the same user`() {
        val user = User.UserBuilder().id(1).build()
        val user2 = User.UserBuilder().id(2).build()
        assertFalse(user == user2)
    }
  */


    @Test
    fun `should increase 10 points and 1 transaction for transactionDuration under 30 min`() {
        val user = User.UserBuilder()
            .point(0)
            .mountCompletedTransactions(0)
            .build()

        user.userUpdateForFinishTransaction(15)
        assertEquals(10, user.point)
        assertEquals(1,user.mountCompletedTransactions)
    }

    @Test
    fun `should increase 10 points and 1 transaction for transactionDuration 30 min `() {
        val user = User.UserBuilder()
            .point(0)
            .mountCompletedTransactions(0)
            .build()

        user.userUpdateForFinishTransaction(30)
        assertEquals(10, user.point)
        assertEquals(1,user.mountCompletedTransactions)
    }

    @Test
    fun `should increase 10 points and 1 transaction for transactionDuration over 30 min `() {
        val user = User.UserBuilder()
            .point(0)
            .mountCompletedTransactions(0)
            .build()

        user.userUpdateForFinishTransaction(45)
        assertEquals(5, user.point)
        assertEquals(1,user.mountCompletedTransactions)
    }


    @Test
    fun `should throw exception for negative time`() {
        val user = User.UserBuilder()
            .point(0)
            .mountCompletedTransactions(0)
            .build()

        assertThrows<TimeoutException> { // mirar
            user.userUpdateForFinishTransaction(-1)
        }
    }

}