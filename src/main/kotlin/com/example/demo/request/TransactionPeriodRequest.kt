package com.example.demo.request

import java.time.LocalDate

class TransactionPeriodRequest(val userId:    String,
                               val startDate: LocalDate,
                               val endDate:   LocalDate) {}