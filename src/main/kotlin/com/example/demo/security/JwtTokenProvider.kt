package com.example.demo.security

import java.util.Date
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class JwtTokenProvider(
    @param:Value("\${app.security.jwt.secret-key}") private val secretKey: String,
    @param:Value("\${app.security.expiration-time}") private val expDate: Long
) {


    fun generateToken(authentication: Authentication): String {
        val email = authentication.name
        val now: Date = Date()
        val expirationToken: Date = Date(now.time + expDate)

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expirationToken)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact() // Genera el token
    }

    fun getEmailFromJwt(token: String?): String {
        val claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
        return claims.subject
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return true
        } catch (ignored: Exception) {
        }
        return false
    }
}
