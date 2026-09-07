package com.example.productservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

data class ErrorResponse(
    val status: Int,
    val message: String
)

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFound(
        exception: ProductNotFoundException
    ): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = exception.message ?: "Product not found"
        )

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response)
    }

    @ExceptionHandler(InsufficientStockException::class)
    fun handleInsufficientStock(
        exception: InsufficientStockException
    ): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = exception.message ?: "Insufficient stock"
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(
        exception: IllegalArgumentException
    ): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = exception.message ?: "Invalid request"
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(
        exception: Exception
    ): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "An unexpected error occurred"
        )

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(response)
    }
}