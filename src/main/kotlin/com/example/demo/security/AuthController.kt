package com.example.demo.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AuthController{

    @Autowired
    lateinit var authService: AuthService


    @PostMapping("/auth/login")
    fun authenticateUser(@RequestBody loginRequest: LoginRequestDTO): ResponseEntity<String> {
        val token = authService.authenticate(loginRequest.mail, loginRequest.password)
        return ResponseEntity.ok(token)
    }

}
