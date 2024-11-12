package com.example.demo.security


import com.example.demo.model.Role
import com.example.demo.repository.UserRepository

import java.util.stream.Collectors

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CustomUserDetailsService @Autowired constructor(userReposit: UserRepository) : UserDetailsService {
    private val userRepository: UserRepository = userReposit

    fun mapToAuthorities(roleList: List<Role>): Collection<GrantedAuthority> {
        return roleList.stream().map { role: Role ->
            SimpleGrantedAuthority(
                role.name
            )
        }.collect(Collectors.toList<GrantedAuthority>())
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByName(username)
            ?: throw UsernameNotFoundException("User not found")

        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            mapToAuthorities(user.roles)
        )
    }
}

