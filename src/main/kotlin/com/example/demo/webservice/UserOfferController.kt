package com.example.demo.webservice

import com.example.demo.dto.UserOfferDTO
import com.example.demo.model.OfferTypeHelper
import com.example.demo.model.UserOffer
import com.example.demo.request.UserOfferRequest
import com.example.demo.service.UserOfferService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class UserOfferController {

    @Autowired
    lateinit var service: UserOfferService

    @Operation(summary = "Publish a new offer")
    @PostMapping("/Offer")
    fun publishOffer (@RequestBody userOfferRequest: UserOfferRequest): ResponseEntity<UserOfferDTO> {
        val userOffer =
            UserOffer.UserOfferBuilder()
                .cryptoSymbol(userOfferRequest.cryptoSymbol)
                .cryptoMounts(userOfferRequest.cryptoMounts)
                .cryptoPrice(userOfferRequest.cryptoPrice)
                .argsMounts(userOfferRequest.argsMounts)
                .userName(userOfferRequest.userName)
                .userLastName(userOfferRequest.userLastName)
                .offerDate(Date())
                .offerType(OfferTypeHelper.transform(userOfferRequest.offerType))
                .build()

        service.publishOffer(userOffer)

        val userOfferDTO = UserOfferDTO(
            userOffer.cryptoSymbol!!,
            userOffer.cryptoMounts!!,
            userOffer.cryptoPrice!!,
            userOffer.argsMounts!!,
            userOffer.userName!!,
            userOffer.userLastName!!,
            userOffer.offerDate!!.toString(),
            userOffer.offerType!!.name)

        return ResponseEntity(userOfferDTO, HttpStatus.CREATED)
    }

    @Operation(summary = "Get all user offers")
    @GetMapping("/offers")
    fun allUserOffers(): List<UserOfferDTO> {
        val usersOffers = service.allOffers().map {
            currentOfferUser: UserOffer ->
                UserOfferDTO(
                    currentOfferUser.cryptoSymbol!!,
                    currentOfferUser.cryptoMounts!!,
                    currentOfferUser.cryptoPrice!!,
                    currentOfferUser.argsMounts!!,
                    currentOfferUser.userName!!,
                    currentOfferUser.userLastName!!,
                    currentOfferUser.offerDate!!.toString(),
                    currentOfferUser.offerType!!.name) }
        return usersOffers
    }

}