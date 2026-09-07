package com.example.orderservice.controller

import com.example.orderservice.dto.CreateOrderRequest
import com.example.orderservice.dto.OrderResponse
import com.example.orderservice.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(
        @RequestBody request: CreateOrderRequest
    ): OrderResponse {

        return orderService.createOrder(request)
    }

    @GetMapping
    fun getAllOrders(): List<OrderResponse> {

        return orderService.getAllOrders()
    }

    @GetMapping("/{id}")
    fun getOrderById(
        @PathVariable id: Long
    ): OrderResponse {

        return orderService.getOrderById(id)
    }

    @GetMapping("/user/{userId}")
    fun getOrdersByUserId(
        @PathVariable userId: Long
    ): List<OrderResponse> {

        return orderService.getOrdersByUserId(userId)
    }

    @PutMapping("/{id}/cancel")
    fun cancelOrder(
        @PathVariable id: Long
    ): OrderResponse {

        return orderService.cancelOrder(id)
    }
}