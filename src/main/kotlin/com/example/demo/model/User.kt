package com.example.demo.model

import com.example.demo.exceptions.UserException
import jakarta.persistence.*
import java.util.regex.Pattern
import kotlin.math.max

@Entity
@Table(name = "user_table")
class User<Date> private constructor(builder: UserBuilder) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = builder.id
    @Column
    var name: String? = builder.name
    @Column
    var lastName: String? = builder.lastName
    @Column(unique = true)
    var email: String? = builder.email
    @Column
    var address: String? = builder.address
    @Column
    var password: String? = builder.password
    @Column
    val cvuMercadoPago: String? = builder.cvuMercadoPago
    @Column
    val cryptoAddress: String? = builder.cryptoAddress
    @Column
    var point: Int = builder.point ?: 0
    @Column
    var mountCompletedTransactions: Int = builder.mountCompletedTransactions ?: 0

    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: List<Role> = ArrayList()


    class UserBuilder {
        var id: Long? = null
            private set
        var name: String? = null
            private set
        var lastName: String? = null
            private set
        var email: String? = null
            private set
        var address: String? = null
            private set
        var password: String? = null
            private set
        var cvuMercadoPago: String? = null
            private set
        var cryptoAddress: String? = null
            private set
        var point: Int? = null  // refactor reputationPoints
            private set
        var mountCompletedTransactions: Int? = null // refactor amountCompletedTransactions
            private set

        fun id(id: Long) = apply {
            this.id = id
        }

        fun name(name: String) = apply {
            require(name.length in 3..30) { "The name must be between 3 and 30 characters long." }
            this.name = name
        }

        fun lastName(lastName: String) = apply {
            require(lastName.length in 3..30) { "The last name must be between 3 and 30 characters long." }
            this.lastName = lastName
        }

        fun email(email: String) = apply {
            require(isValidEmail(email)) { "The email format is not valid." }
            this.email = email
        }

        fun address(address: String) = apply {
            require(address.length in 10..30) { "The address must be between 10 and 30 characters long." }
            this.address = address
        }

        fun password(password: String) = apply {
            require(isValidPassword(password)) { "The password must have at least 1 lowercase letter, 1 uppercase letter, 1 special character, and be at least 6 characters long." }
            this.password = password
        }

        fun cvuMercadoPago(cvuMercadoPago: String) = apply {
            require(cvuMercadoPago.length == 22) { "The MercadoPago CVU must be 22 digits long." }
            this.cvuMercadoPago = cvuMercadoPago
        }

        fun cryptoAddress(cryptoAddress: String) = apply {
            require(cryptoAddress.length == 8) { "The crypto wallet address must be 8 digits long." }
            this.cryptoAddress = cryptoAddress
        }

        fun point(point: Int) = apply {
            require(point >= 0) { "Points cannot be negative." }
            this.point = point
        }

        fun mountCompletedTransactions(mountCompletedTransactions: Int) = apply {
            require(mountCompletedTransactions >= 0) { "Completed transactions cannot be negative." }
            this.mountCompletedTransactions = mountCompletedTransactions
        }

        fun build(): User<Any?> {
            return User(this)
        }

        private fun isValidEmail(email: String?): Boolean {
            val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
            val pattern = Pattern.compile(emailRegex)
            return pattern.matcher(email).matches()
        }

        private fun isValidPassword(password: String?): Boolean {
            val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^*()&+=])(?=\\S+\$).{6,}\$"
            val pattern = Pattern.compile(passwordRegex)
            return pattern.matcher(password).matches()
        }
    }

    fun userName() : String = this.name!!

    fun userLastName(): String = this.lastName!!

    override fun equals(other: Any?): Boolean {
        return (other is User<*>) && (this.id == other.id)
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun userUpdateForFinishTransaction(transactionDuration: Long) {
        this.mountCompletedTransactions += 1
        this.point +=  this.reputationPointsToaAdd(transactionDuration)
    }

    private fun reputationPointsToaAdd(transactionDuration: Long): Int {
        if(transactionDuration < 0){throw UserException("Transaction duration error")
        }
        return when (transactionDuration <= 30) {
            true  -> 10
            false -> 5
        }
    }

    fun userUpdateForCancelTransaction() { this.point = max(0,this.point - 20) }

    fun reputation(): Int {
        return if(this.mountCompletedTransactions > 0){
            this.point / this.mountCompletedTransactions
        }else{
            0
        }
    }
}
