package com.example.productservice.service

import com.example.productservice.dto.CreateProductRequest
import com.example.productservice.dto.ProductResponse
import com.example.productservice.dto.StockOperation
import com.example.productservice.dto.StockUpdateRequest
import com.example.productservice.dto.UpdateProductRequest
import com.example.productservice.entity.Product
import com.example.productservice.exception.InsufficientStockException
import com.example.productservice.exception.ProductNotFoundException
import com.example.productservice.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    fun createProduct(
        request: CreateProductRequest
    ): ProductResponse {

        validateProductData(request.name, request.price, request.stock)

        val product = Product(
            name = request.name,
            price = request.price,
            stock = request.stock
        )

        val savedProduct = productRepository.save(product)

        return savedProduct.toResponse()
    }

    fun getAllProducts(): List<ProductResponse> {

        return productRepository.findAll()
            .map { it.toResponse() }
    }

    fun getProductById(id: Long): ProductResponse {

        val product = findProduct(id)

        return product.toResponse()
    }

    fun updateProduct(
        id: Long,
        request: UpdateProductRequest
    ): ProductResponse {

        validateProductData(request.name, request.price, request.stock)

        val product = findProduct(id)

        product.name = request.name
        product.price = request.price
        product.stock = request.stock

        val updatedProduct = productRepository.save(product)

        return updatedProduct.toResponse()
    }

    fun deleteProduct(id: Long) {

        if (!productRepository.existsById(id)) {
            throw ProductNotFoundException(
                "Product with id $id not found"
            )
        }

        productRepository.deleteById(id)
    }

    @Transactional
    fun updateStock(
        id: Long,
        request: StockUpdateRequest
    ): ProductResponse {

        if (request.quantity <= 0) {
            throw IllegalArgumentException(
                "Stock quantity must be greater than 0"
            )
        }

        val product = findProduct(id)

        when (request.operation) {

            StockOperation.DECREASE -> {

                if (product.stock < request.quantity) {
                    throw InsufficientStockException(
                        "Insufficient stock for product with id $id"
                    )
                }

                product.stock -= request.quantity
            }

            StockOperation.INCREASE -> {
                product.stock += request.quantity
            }
        }

        val updatedProduct = productRepository.save(product)

        return updatedProduct.toResponse()
    }

    private fun findProduct(id: Long): Product {

        return productRepository.findById(id)
            .orElseThrow {
                ProductNotFoundException(
                    "Product with id $id not found"
                )
            }
    }

    private fun validateProductData(
        name: String,
        price: Double,
        stock: Int
    ) {

        if (name.isBlank()) {
            throw IllegalArgumentException(
                "Product name cannot be empty"
            )
        }

        if (price < 0) {
            throw IllegalArgumentException(
                "Product price cannot be negative"
            )
        }

        if (stock < 0) {
            throw IllegalArgumentException(
                "Product stock cannot be negative"
            )
        }
    }

    private fun Product.toResponse(): ProductResponse {

        return ProductResponse(
            id = id,
            name = name,
            price = price,
            stock = stock
        )
    }
}