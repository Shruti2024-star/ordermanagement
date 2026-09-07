package com.example.orderservice.client

import com.example.orderservice.dto.StockUpdateRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Double,
    val stock: Int
)

@FeignClient(name = "product-service")
interface ProductClient {

    @GetMapping("/products/{id}")
    fun getProductById(
        @PathVariable id: Long
    ): ProductResponse

    @PutMapping("/products/{id}/stock")
    fun updateStock(
        @PathVariable id: Long,
        @RequestBody request: StockUpdateRequest
    ): ProductResponse
}