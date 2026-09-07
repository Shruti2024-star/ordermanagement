package com.example.productservice.entity


import jakarta.persistence.*

@Entity
@Table(name = "products")
class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var price: Double,

    @Column(nullable = false)
    var stock: Int
)