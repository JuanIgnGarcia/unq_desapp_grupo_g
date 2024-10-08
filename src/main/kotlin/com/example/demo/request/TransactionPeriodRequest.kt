package com.example.demo.request

import java.time.LocalDate
import org.springframework.aot.generate.Generated

@Generated
class TransactionPeriodRequest(val userId:    String,
                               val startDate: LocalDate,
                               val endDate:   LocalDate) {}