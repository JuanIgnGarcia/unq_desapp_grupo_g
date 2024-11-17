package com.example.demo.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val authService: AuthService) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val secretKey = authService.secretKey

        http.csrf().disable()
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers(
                        "/auth/login",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/h2-console/**",
                        "/register"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(JwtAuthenticationFilter(secretKey), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
