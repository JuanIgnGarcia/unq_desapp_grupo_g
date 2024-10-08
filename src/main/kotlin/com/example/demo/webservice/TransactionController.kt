package com.example.demo.webservice

import com.example.demo.dto.TransactionDTO
import com.example.demo.service.TransactionService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionController {

    @Autowired
    lateinit var service: TransactionService


    @Operation(summary = "Accept offer")
    @PostMapping("/transaction/AcceptOffer/{userId}/{offerId}")
    fun acceptOffer (@PathVariable userId: String, @PathVariable offerId: String): ResponseEntity<TransactionDTO> {

        val transactionDTO = service.createTransaction(userId,offerId)

        return ResponseEntity(transactionDTO, HttpStatus.CREATED)
    }

    @Operation(summary = "make a transfer")
    @PostMapping("/transaction/makeTransfer/{userId}/{transactionId}")
    fun makeTransfer(@PathVariable userId: String, @PathVariable transactionId: String): ResponseEntity<Void> {
        service.makeTransfer(userId,transactionId)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @Operation(summary = "Confirm receipt")
    @PostMapping("/transaction/confirmReceipt/{userId}/{transactionId}")
    fun confirmReceipt(@PathVariable userId: String, @PathVariable transactionId: String): ResponseEntity<Void> {
        service.confirmReceipt(userId,transactionId)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @Operation(summary = "Cancel a active transaction")
    @PostMapping("/transaction/cancel/{userId}/{transactionId}")
    fun cancelTransaction(@PathVariable userId: String, @PathVariable transactionId: String): ResponseEntity<Void> {
        service.cancelTransaction(userId,transactionId)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @Operation(summary = "all transactions")
    @PostMapping("/transactions")
    fun allTransactions(): List<TransactionDTO> {
        val transactionsDTO = service.allTransactions()

        return transactionsDTO
    }

    // Dado un usuario,  Informar el volumen operado de cripto activos entre dos fechas.


}