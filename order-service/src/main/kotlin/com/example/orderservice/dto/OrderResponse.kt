package com.example.orderservice.dto

import com.example.orderservice.entity.OrderStatus

data class OrderResponse(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val quantity: Int,
    val totalPrice: Double,
    val status: OrderStatus
)