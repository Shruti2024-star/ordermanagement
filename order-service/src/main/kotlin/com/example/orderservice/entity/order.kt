package com.example.orderservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "orders")
class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val productId: Long,

    @Column(nullable = false)
    val quantity: Int,

    @Column(nullable = false)
    val totalPrice: Double,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: OrderStatus = OrderStatus.CREATED
)

enum class OrderStatus {
    CREATED,
    CANCELLED
}