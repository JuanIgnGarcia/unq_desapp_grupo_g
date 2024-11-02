package com.example.demo.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Override
    protected fun configure(http: HttpSecurity)  {
        http.csrf().disable().authorizeRequests().anyRequest().permitAll()
    }
}