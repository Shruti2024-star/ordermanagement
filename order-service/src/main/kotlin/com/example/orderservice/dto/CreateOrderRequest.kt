package com.example.orderservice.dto

data class CreateOrderRequest(
    val userId: Long,
    val productId: Long,
    val quantity: Int
)