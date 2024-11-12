package com.example.demo.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class JwtAuthenticationFilter @Autowired constructor(
    private val customUserDetailsService: CustomUserDetailsService,
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = getTokenFromRequest(request)
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            val email = jwtTokenProvider.getEmailFromJwt(token)
            val userDetails = customUserDetailsService.loadUserByUsername(email)

            val authenticationToken = UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.authorities
            )
            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authenticationToken
        }

        filterChain.doFilter(request, response)
    }
}
