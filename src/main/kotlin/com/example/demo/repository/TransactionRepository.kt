package com.example.demo.repository

import com.example.demo.model.Transaction
import com.example.demo.model.TransactionStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun findAllByOfferUserIdAndStartTimeBetweenAndTransactionStatus(
        userId: Long,
        startDate: Date,
        endDate: Date,
        status: TransactionStatus
    ): List<Transaction>

}