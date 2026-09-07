package com.example.userservice.exceptions

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

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(
        exception: UserNotFoundException
    ): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = exception.message ?: "User not found"
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(response)
    }

    @ExceptionHandler(DuplicateEmailException::class)
    fun handleDuplicateEmail(
        exception: DuplicateEmailException
    ): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            message = exception.message ?: "Email already exists"
        )

        return ResponseEntity.status(HttpStatus.CONFLICT)
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

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(response)
    }
}