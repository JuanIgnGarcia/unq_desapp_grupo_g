package com.example.demo.dto

import org.springframework.aot.generate.Generated

@Generated
class TransactionDTO (val id : String,
                      val criptosymbol: String,
                      val cryptoMounts :Double,
                      val cryptoPrice: Double,
                      val totalPrice: Double,
                      val biddingUserId: String,
                      val biddingUserName: String,
                      val biddingUserLastName: String,
                      val biddingUserAmountOperations: Int,
                      val biddingUserReputation: Int,
                      val mailingAddress: String,
                      val acceptingUserId : String
                      ) {}