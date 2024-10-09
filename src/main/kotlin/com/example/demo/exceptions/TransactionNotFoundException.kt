package com.example.demo.exceptions
import org.springframework.aot.generate.Generated

@Generated
class TransactionNotFoundException(message: String) : RuntimeException(message) {}