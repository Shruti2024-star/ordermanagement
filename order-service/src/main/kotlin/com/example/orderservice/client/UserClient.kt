package com.example.orderservice.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String
)

@FeignClient(name = "user-service")
interface UserClient {

    @GetMapping("/users/{id}")
    fun getUserById(
        @PathVariable id: Long
    ): UserResponse
}