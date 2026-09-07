package com.example.userservice.exceptions

class UserNotFoundException(
    message: String
) : RuntimeException(message)