package com.example.orderservice.service

import com.example.orderservice.client.ProductClient
import com.example.orderservice.client.UserClient
import com.example.orderservice.dto.CreateOrderRequest
import com.example.orderservice.dto.OrderResponse
import com.example.orderservice.dto.StockOperation
import com.example.orderservice.dto.StockUpdateRequest
import com.example.orderservice.entity.Order
import com.example.orderservice.entity.OrderStatus
import com.example.orderservice.exception.OrderCancellationException
import com.example.orderservice.exception.OrderNotFoundException
import com.example.orderservice.exception.ProductNotFoundException
import com.example.orderservice.exception.UserNotFoundException
import com.example.orderservice.repository.OrderRepository
import feign.FeignException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val userClient: UserClient,
    private val productClient: ProductClient
) {

    @Transactional
    fun createOrder(
        request: CreateOrderRequest
    ): OrderResponse {

        if (request.quantity <= 0) {
            throw IllegalArgumentException(
                "Quantity must be greater than 0"
            )
        }

        // 1. Check user
        val user = try {
            userClient.getUserById(request.userId)
        } catch (exception: FeignException.NotFound) {
            throw UserNotFoundException(
                "User with id ${request.userId} not found"
            )
        }

        // 2. Check product
        val product = try {
            productClient.getProductById(request.productId)
        } catch (exception: FeignException.NotFound) {
            throw ProductNotFoundException(
                "Product with id ${request.productId} not found"
            )
        }

        // 3. Check stock
        if (product.stock < request.quantity) {
            throw IllegalArgumentException(
                "Insufficient stock for product with id ${request.productId}"
            )
        }

        // 4. Calculate total
        val totalPrice = product.price * request.quantity

        // 5. Reduce product stock
        productClient.updateStock(
            request.productId,
            StockUpdateRequest(
                quantity = request.quantity,
                operation = StockOperation.DECREASE
            )
        )

        // 6. Save order
        val order = Order(
            userId = user.id,
            productId = product.id,
            quantity = request.quantity,
            totalPrice = totalPrice
        )

        val savedOrder = orderRepository.save(order)

        return savedOrder.toResponse()
    }

    fun getAllOrders(): List<OrderResponse> {

        return orderRepository.findAll()
            .map { it.toResponse() }
    }

    fun getOrderById(id: Long): OrderResponse {

        val order = findOrder(id)

        return order.toResponse()
    }

    fun getOrdersByUserId(userId: Long): List<OrderResponse> {

        return orderRepository.findByUserId(userId)
            .map { it.toResponse() }
    }

    @Transactional
    fun cancelOrder(id: Long): OrderResponse {

        val order = findOrder(id)

        if (order.status == OrderStatus.CANCELLED) {
            throw OrderCancellationException(
                "Order with id $id is already cancelled"
            )
        }

        // Give stock back to Product Service
        productClient.updateStock(
            order.productId,
            StockUpdateRequest(
                quantity = order.quantity,
                operation = StockOperation.INCREASE
            )
        )

        order.status = OrderStatus.CANCELLED

        val cancelledOrder = orderRepository.save(order)

        return cancelledOrder.toResponse()
    }

    private fun findOrder(id: Long): Order {

        return orderRepository.findById(id)
            .orElseThrow {
                OrderNotFoundException(
                    "Order with id $id not found"
                )
            }
    }

    private fun Order.toResponse(): OrderResponse {

        return OrderResponse(
            id = id,
            userId = userId,
            productId = productId,
            quantity = quantity,
            totalPrice = totalPrice,
            status = status
        )
    }
}