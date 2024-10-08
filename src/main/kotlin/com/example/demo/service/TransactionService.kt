package com.example.demo.service

import com.example.demo.dto.TransactionDTO
import com.example.demo.model.*
//import com.example.demo.exceptions.OfferTypeException
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

        val offer = findUserOffer(offerId)

        val acceptingUser = findUser(userId)

        try {
            val transaction = Transaction.TransactionBuilder()
                .offer(offer)
                .acceptingUser(acceptingUser)
                .startTime(Date())
                .transactionStatus(TransactionStatus.ACTIVE)
                .build()

            val transactionDTO = transactionToDto(transaction,mailingAddress(offer))
            offer.invalidate()
            userOfferRepository.save(offer)  // necesario ?
            transactionRepository.save(transaction)
            return transactionDTO

        }catch (e: Exception) {
            throw TimeoutException("Failed to create transaction: ${e.message}") //TransactionCreationException("Failed to create transaction: ${e.message}")
        }
    }

    fun makeTransfer(userId: String, transactionId: String) {
        val user = findUser(userId)
        val transaction = transactionRepository.findById(transactionId.toLong()).
        orElseThrow{ throw TimeoutException("Offer $transactionId not found") //TransactionNotFoundException("Transaction $transactionId not found")
        }
        transaction.makeTransfer(user)
        transactionRepository.save(transaction)
    }

    fun confirmReceipt(userId: String, transactionId: String) {
        val user = findUser(userId)
        val transaction = transactionRepository.findById(transactionId.toLong()).
        orElseThrow{ throw TimeoutException("Offer $transactionId not found") //TransactionNotFoundException("Transaction $transactionId not found")
        }
        transaction.confirmReceipt(user)
        transactionRepository.save(transaction)
    }

    fun cancelTransaction(userId: String, transactionId: String) {
        val user = findUser(userId)
        val transaction = transactionRepository.findById(transactionId.toLong()).
        orElseThrow{ throw TimeoutException("Offer $transactionId not found") //TransactionNotFoundException("Transaction $transactionId not found")
        }
        transaction.cancelTransaction(user)
        transactionRepository.save(transaction)
    }

    fun allTransactions(): List<TransactionDTO> {
        val transactions = transactionRepository.findAll().map { transaction -> transactionToDto(transaction,mailingAddress(transaction.offer!!))}

        return transactions
    }

    private fun findUser(userId: String): User<Any?> {  // refactor
        return userRepository.findById(userId.toLong()).
        orElseThrow{ throw TimeoutException("Offer $userId not found") //UserNotFoundException("User $userId not found")
        }
    }

    private fun findUserOffer(offerId: String): UserOffer {  // refactor
        return userOfferRepository.findById(offerId.toLong()).
        orElseThrow { throw TimeoutException("Offer $offerId not found:") //UserOfferNotFoundException("Offer $offerId not found")
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