package com.example.demo.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Component
class JwtProvider {

    @Value("\${jwt.secret}")
    lateinit var secret: String

    @PostConstruct
    protected fun init() {
        secret = Base64.getEncoder().encodeToString(secret.toByteArray())
    }

    fun createToken(authUser: AuthUser): String {
        val claims: MutableMap<String, Any> = HashMap()
        claims["sub"] = authUser.userName
        claims["id"] = authUser.id

        val now = Date()
        val exp = Date(now.time + 3600000)

        return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS256,secret)
                    .compact()

    }


    fun validate(token : String) : Boolean{
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return true
        }catch (e : Exception ){
            return false
        }
    }

    fun getUserNameFromToken(token: String) : String{
        return try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject
        }catch (e : Exception ){
            "bad token"
        }
    }

}
