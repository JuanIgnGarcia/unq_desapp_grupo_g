package com.example.demo.request

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import org.springframework.aot.generate.Generated

@Generated
class TransactionPeriodRequest(
    val userId: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val startDate: LocalDate,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val endDate: LocalDate
    ){}