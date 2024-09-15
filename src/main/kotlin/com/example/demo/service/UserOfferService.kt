package com.example.demo.service

import com.example.demo.Exceptions.UserCreationException
import com.example.demo.model.UserOffer
import com.example.demo.repository.UserOfferRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserOfferService {

    @Autowired
    lateinit var repository : UserOfferRepository

    fun publishOffer(userOffer: UserOffer) : UserOffer{
        try {
            repository.save(userOffer)
            return userOffer
        }catch (e: Exception) {
            throw UserCreationException("Failed to create user: ${e.message}") // Change
        }
    }

    fun allOffers(): List<UserOffer>{
        return repository.findAll()
    }



}

