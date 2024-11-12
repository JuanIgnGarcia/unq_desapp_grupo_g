package com.example.demo.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
//        val jsonError = JSONObject()
//        jsonError.put("Error", "unavailable session")
//        response.status = HttpServletResponse.SC_UNAUTHORIZED
//        response.contentType = "application/json;charset=UTF-8"
//        response.writer.print(jsonError)
    }
}