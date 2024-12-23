package com.example.demo.repository

import com.example.demo.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role?, Long?> {
    fun findByName(name: String?): Optional<Role?>?
}



