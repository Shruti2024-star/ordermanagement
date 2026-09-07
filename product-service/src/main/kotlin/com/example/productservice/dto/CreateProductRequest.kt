package com.example.productservice.dto


data class CreateProductRequest(
    val name: String,
    val price: Double,
    val stock: Int
)