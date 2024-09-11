package com.example.demo.model

import jakarta.persistence.*
import java.util.regex.Pattern

@Entity
@Table(name = "user_table")
class User() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @Column
    var name: String? = null
    @Column
    var lastName: String? = null
    @Column(unique = true)
    var email: String? = null
    @Column
    var address: String? = null
    @Column
    var password: String? = null
    @Column
    var cvuMercadoPago: String? = null
    @Column
    var cryptoAddress: String? = null
    @Column
    var point : Int = 0

    constructor(nameU: String, lastNameU: String, emailU: String,  addressU: String , passwordU: String, cvuMP: String, cryptoAddU : String) : this() {
        this.name = nameU
        this.lastName = lastNameU
        this.email = emailU
        this.address = addressU
        this.password = passwordU
        this.cvuMercadoPago = cvuMP
        this.cryptoAddress = cryptoAddU

        require(name?.length in 3..30) { "The name must be between 3 and 30 characters long." }
        require(lastName?.length in 3..30) { "The last name must be between 3 and 30 characters long." }
        require(isValidEmail(email)) { "The email format is not valid." }
        require(address?.length in 10..30) { "The address must be between 10 and 30 characters long." }
        require(isValidPassword(password)) { "The password must have at least 1 lowercase letter, 1 uppercase letter, 1 special character, and be at least 6 characters long." }
        require(cvuMercadoPago?.length == 22) { "The MercadoPago CVU must be 22 digits long." }
        require(cryptoAddress?.length == 8) { "The crypto wallet address must be 8 digits long." }

    }

    private fun isValidEmail(email: String?): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }

    private fun isValidPassword(password: String?): Boolean {
        //a-z contiene al menos 1 minuscula, A-Z al menos 1 mayuscula, !@#$%^&*()=+ caracteres especiales, \\S+\$ que no tenga espacios, {6,} al menos 6 chars
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^*()&+=])(?=\\S+\$).{6,}\$"
        val pattern = Pattern.compile(passwordRegex)
        return pattern.matcher(password).matches()
    }
}