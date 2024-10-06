package com.example.demo.webservice

import com.example.demo.dto.TransactionDTO
import com.example.demo.request.TransactionRequest
import com.example.demo.service.TransactionService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionController {

    @Autowired
    lateinit var service: TransactionService


    @Operation(summary = "Accept offer")
    @PostMapping("/AcceptOffer")
    fun acceptOffer (@RequestBody transactionRequest: TransactionRequest): ResponseEntity<TransactionDTO> {

        val transactionDTO = service.createTransaction(transactionRequest.userId,transactionRequest.offerId)

        return ResponseEntity(transactionDTO, HttpStatus.CREATED)
    }


    // Realice la transferencia (Si el usuario es comprador)

    // Confirmar recepción  (Si el usuario es vendedor)

    // Cancelar

    // Dado un usuario,  Informar el volumen operado de cripto activos entre dos fechas.


}