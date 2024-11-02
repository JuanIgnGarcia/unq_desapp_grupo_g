package com.example.demo.security

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthUserRepository : JpaRepository<AuthUser, Long> {

    fun findByUserName( userName: String): Optional<AuthUser>

}