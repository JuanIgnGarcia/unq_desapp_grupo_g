package com.example.demo.service

import com.example.demo.dto.TransactionDTO
//import com.example.demo.exceptions.OfferTypeException
import com.example.demo.model.OfferType
import com.example.demo.model.Transaction
import com.example.demo.model.TransactionStatus
import com.example.demo.model.UserOffer
import com.example.demo.repository.TransactionRepository
import com.example.demo.repository.UserOfferRepository
import com.example.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeoutException

@Service
class TransactionService {

    @Autowired
    lateinit var transactionRepository : TransactionRepository
    @Autowired
    lateinit var userOfferRepository : UserOfferRepository
    @Autowired
    lateinit var userRepository : UserRepository

    fun createTransaction(userId: String, offerId: String) : TransactionDTO {


        val offer = userOfferRepository.findById(offerId.toLong()).
            orElseThrow { throw TimeoutException("Offer $offerId not found:") //UserOfferNotFoundException("Offer $offerId not found")
            }

        val acceptingUser = userRepository.findById(userId.toLong()).
            orElseThrow{ throw TimeoutException("Offer $offerId not found") //UserNotFoundException("User $userId not found")
            }

        try {
            val transaction = Transaction.TransactionBuilder()
                .offer(offer)
                .acceptingUser(acceptingUser)
                .startTime(Date())
                .transactionStatus(TransactionStatus.ACTIVE)
                .build()

            val transactionDTO = this.transactionToDto(transaction,this.mailingAddress(offer!!))
            offer.invalidate()
            userOfferRepository.save(offer)  // necesario
            transactionRepository.save(transaction)
            return transactionDTO

        }catch (e: Exception) {
            throw TimeoutException("Failed to create transaction: ${e.message}") //TransactionCreationException("Failed to create transaction: ${e.message}")
        }

    }

    private fun mailingAddress(offer: UserOffer): String {
        return when (offer.offerType) {
            OfferType.BUY  -> offer.user!!.cryptoAddress!!
            OfferType.SELL -> offer.user!!.cvuMercadoPago!!
            else -> {
                throw TimeoutException("Invalid Offer type") // throw OfferTypeException("Invalid Offer type")
            }
        }
    }


    private fun transactionToDto(transaction: Transaction,mailingAddress : String): TransactionDTO {
        val transactionOffer = transaction.offer!!

        val transactionDTO = TransactionDTO(
            id                          = transaction.id.toString(),
            criptosymbol                = transactionOffer.cryptoSymbol!!,
            cryptoMounts                = transactionOffer.cryptoMounts!!,
            cryptoPrice                 = transactionOffer.cryptoPrice!!,
            totalPrice                  = transactionOffer.cryptoMounts!! * transactionOffer.cryptoPrice!!,
            biddingUserId               = transactionOffer.user!!.id.toString(),
            biddingUserName             = transactionOffer.userName(),
            biddingUserLastName         = transactionOffer.userLastName(),
            biddingUserAmountOperations = transactionOffer.user!!.mountCompletedTransactions,
            biddingUserReputation       = transactionOffer.user!!.point,
            mailingAddress              = mailingAddress,
            acceptingUserId             = transaction.acceptingUser!!.id.toString()
        )
        return transactionDTO
    }


}