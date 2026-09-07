package com.example.productservice.dto

data class UpdateProductRequest(
    val name: String,
    val price: Double,
    val stock: Int
)