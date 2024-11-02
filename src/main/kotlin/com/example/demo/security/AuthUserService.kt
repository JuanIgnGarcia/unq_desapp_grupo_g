package com.example.demo.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthUserService {

    @Autowired
    lateinit var authUserRepository : AuthUserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var jwtProvider : JwtProvider


    fun save(dto: AuthUserDTO) : AuthUser?{
        val user : Optional<AuthUser> = authUserRepository.findByUserName(dto.userName)
        if (!user.isPresent){
            return null
        }
        var password : String = passwordEncoder.encode(dto.password)
        val authUser = AuthUser(userName = dto.userName, password = dto.password)

        return  authUserRepository.save(authUser)
    }

    fun login(dto: AuthUserDTO) : TokenDTO? {
        val user : Optional<AuthUser>  = authUserRepository.findByUserName(dto.userName)
        if (!user.isPresent){
            return null
        }
        if (passwordEncoder.matches(dto.password,user.get().password)){
            return TokenDTO(jwtProvider.createToken(user.get()))
        }
        return null
    }

    fun validate(token: String) : TokenDTO? {
        if(!jwtProvider.validate(token)){
            return null
        }

        val username : String = jwtProvider.getUserNameFromToken(token)

        if(!authUserRepository.findByUserName(username).isPresent){
            return null
        }

        return TokenDTO(token)
    }

}