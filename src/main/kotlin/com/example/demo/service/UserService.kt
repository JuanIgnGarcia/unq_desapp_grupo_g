package com.example.demo.service

import com.example.demo.Exceptions.UserCreationException
import com.example.demo.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.example.demo.repository.UserRepository

@Service
class UserService {

    @Autowired
    lateinit var repo : UserRepository

    fun createUser(user : User): User {
        try {
            repo.save(user)
            return user
        }catch (e: Exception) {
            throw UserCreationException("Failed to create user: ${e.message}")
        }
    }

    fun allUsers(): List<User>{
        return repo.findAll()
    }

}