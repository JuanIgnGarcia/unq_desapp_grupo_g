package com.example.demo.security

import com.example.demo.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class AuthService(
    private val userRepository: UserRepository
) {
    val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun authenticate(email: String, password: String): String {

        val user = userRepository.findByEmail(email) ?: throw IllegalArgumentException("Usuario no encontrado")

        if (!validatePassword(password, user.password!!)) {
            throw IllegalArgumentException("Credenciales inválidas")
        }

        // Generar token JWT
        return generateToken(user.email!!)
    }

    private fun validatePassword(rawPassword: String, encodedPassword: String): Boolean {
        return rawPassword == encodedPassword
    }

    private fun generateToken(email: String): String {
        val now = Date()
        val expiryDate = Date(now.time + 3600_000) // Expira en 1 hora

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey)
            .compact()
    }
}


