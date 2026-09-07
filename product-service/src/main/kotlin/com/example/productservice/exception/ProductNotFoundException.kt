package com.example.productservice.exception


class ProductNotFoundException(
    message: String
) : RuntimeException(message)