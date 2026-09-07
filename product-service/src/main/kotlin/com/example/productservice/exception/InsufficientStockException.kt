package com.example.productservice.exception


class InsufficientStockException(
    message: String
) : RuntimeException(message)