package com.example.demo.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthUserController {

    @Autowired
    lateinit var authUserService : AuthUserService


    @PostMapping("/login")
    fun login(@RequestBody dto:AuthUserDTO) : ResponseEntity<TokenDTO>{
        val tokenDto : TokenDTO? = authUserService.login(dto)
        if(tokenDto == null){
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.ok(tokenDto)
    }

    @PostMapping("/validate")
    fun validate(@RequestParam token :String) : ResponseEntity<TokenDTO>{
        val tokenDto : TokenDTO? = authUserService.validate(token)
        if (tokenDto == null){
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.ok(tokenDto)
    }

    @PostMapping("/create")
    fun create(@RequestBody dto:AuthUserDTO) : ResponseEntity<AuthUser>{
        val authUser : AuthUser? = authUserService.save(dto)
        if(authUser == null){
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.ok(authUser)
    }


}