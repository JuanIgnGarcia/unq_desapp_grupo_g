//package com.example.demo.security
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.web.bind.annotation.*
//
//@RestController
//@RequestMapping("/auth")
//class AuthController @Autowired constructor(
//    private val authService: AuthService
//) {
//
//    @PostMapping("/login")
//    fun authenticateUser(@RequestBody loginRequest: LoginRequestDTO): Map<String, String> {
//        val jwt = authService.authenticate(loginRequest.username, loginRequest.password)
//        return mapOf("token" to jwt)
//    }
//}
