package com.example.orderservice.exception

class ProductNotFoundException(
    message: String
) : RuntimeException(message)