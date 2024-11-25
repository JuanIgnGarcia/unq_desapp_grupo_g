package com.example.demo

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import kotlin.system.measureTimeMillis

@Component
class AuditInterceptor : HandlerInterceptor {

    private val logger: Logger = LogManager.getLogger(AuditInterceptor::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val startTime = System.currentTimeMillis()

        // Obtener información de la solicitud
        val timestamp = System.currentTimeMillis()
        val user = SecurityContextHolder.getContext().authentication?.name ?: "anonymous"
        val method = request.method
        val uri = request.requestURI
        val parameters = request.parameterMap.map { "${it.key}=${it.value.joinToString()}" }.joinToString(", ")

        // Log de inicio
        val executionTime = measureTimeMillis {
            logger.info("Timestamp: $timestamp, User: $user, Method: $method $uri, Parameters: $parameters")
        }

        // Guardar el tiempo de ejecución
        logger.info("Execution Time for $method $uri: ${System.currentTimeMillis() - startTime} ms")

        return true
    }
}
