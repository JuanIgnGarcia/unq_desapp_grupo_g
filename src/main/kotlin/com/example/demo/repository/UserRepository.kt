package com.example.demo.repository

import com.example.demo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User<Any?>, Long> {
    fun findByName(username: String?): User<Any?>

    fun findByEmail(email: String): User<Any?>

}