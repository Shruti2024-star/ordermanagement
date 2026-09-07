package com.example.productservice.controller


import com.example.productservice.dto.CreateProductRequest
import com.example.productservice.dto.ProductResponse
import com.example.productservice.dto.StockUpdateRequest
import com.example.productservice.dto.UpdateProductRequest
import com.example.productservice.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(
        @RequestBody request: CreateProductRequest
    ): ProductResponse {

        return productService.createProduct(request)
    }

    @GetMapping
    fun getAllProducts(): List<ProductResponse> {

        return productService.getAllProducts()
    }

    @GetMapping("/{id}")
    fun getProductById(
        @PathVariable id: Long
    ): ProductResponse {

        return productService.getProductById(id)
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody request: UpdateProductRequest
    ): ProductResponse {

        return productService.updateProduct(id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(
        @PathVariable id: Long
    ) {

        productService.deleteProduct(id)
    }

    @PutMapping("/{id}/stock")
    fun updateStock(
        @PathVariable id: Long,
        @RequestBody request: StockUpdateRequest
    ): ProductResponse {

        return productService.updateStock(id, request)
    }
}