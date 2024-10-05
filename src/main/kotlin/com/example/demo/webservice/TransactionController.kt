package com.example.demo.webservice

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

    /*
    @Operation(summary = "Accept offer")
    @PostMapping("/AcceptOffer/{}")
    fun acceptOffer (@RequestBody transactionRequest: TransactionRequest): ResponseEntity<TransactionRequest> {


        return ResponseEntity(transactionRequest, HttpStatus.CREATED)
    }
    */


}