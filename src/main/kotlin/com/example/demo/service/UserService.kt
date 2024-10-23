package com.example.demo.service

import com.example.demo.exceptions.UserCreationException
import com.example.demo.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.example.demo.repository.UserRepository
import org.springframework.aot.generate.Generated

@Generated
@Service
class UserService {

    @Autowired
    lateinit var repo : UserRepository

    fun createUser(user : User<Any?>): User<Any?> {
        try {
            repo.save(user)
            return user
        }catch (e: Exception) {
            throw UserCreationException("Failed to create user: ${e.message}")
        }
    }

    fun allUsers(): List<User<Any?>>{
        return repo.findAll()
    }

}