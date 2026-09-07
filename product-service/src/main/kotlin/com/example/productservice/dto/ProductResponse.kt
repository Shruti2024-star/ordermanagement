package com.example.productservice.dto

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Double,
    val stock: Int
)