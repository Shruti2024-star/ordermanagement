package com.example.orderservice.dto

data class StockUpdateRequest(
    val quantity: Int,
    val operation: StockOperation
)

enum class StockOperation {
    INCREASE,
    DECREASE
}