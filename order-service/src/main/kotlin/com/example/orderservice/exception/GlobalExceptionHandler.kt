package com.example.orderservice.exception

import feign.FeignException
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

    @ExceptionHandler(OrderNotFoundException::class)
    fun handleOrderNotFound(
        exception: OrderNotFoundException
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    exception.message ?: "Order not found"
                )
            )
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(
        exception: UserNotFoundException
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    exception.message ?: "User not found"
                )
            )
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFound(
        exception: ProductNotFoundException
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    exception.message ?: "Product not found"
                )
            )
    }

    @ExceptionHandler(OrderCancellationException::class)
    fun handleCancellation(
        exception: OrderCancellationException
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    exception.message ?: "Order cannot be cancelled"
                )
            )
    }

    @ExceptionHandler(FeignException::class)
    fun handleFeignException(
        exception: FeignException
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.BAD_GATEWAY)
            .body(
                ErrorResponse(
                    HttpStatus.BAD_GATEWAY.value(),
                    "Unable to communicate with another service"
                )
            )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(
        exception: IllegalArgumentException
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    exception.message ?: "Invalid request"
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(
        exception: Exception
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected error occurred"
                )
            )
    }
}