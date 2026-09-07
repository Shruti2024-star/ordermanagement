package com.example.productservice.dto


data class StockUpdateRequest(
    val quantity: Int,
    val operation: StockOperation
)

enum class StockOperation {
    INCREASE,
    DECREASE
}