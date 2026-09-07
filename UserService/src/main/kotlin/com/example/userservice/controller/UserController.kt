package com.example.userservice.controller

import com.example.userservice.dto.CreateUserRequest
import com.example.userservice.dto.UpdateUserRequest
import com.example.userservice.dto.UserResponse
import com.example.userservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(
        @RequestBody request: CreateUserRequest
    ): UserResponse {

        return userService.createUser(request)
    }

    @GetMapping
    fun getAllUsers(): List<UserResponse> {

        return userService.getAllUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(
        @PathVariable id: Long
    ): UserResponse {

        return userService.getUserById(id)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody request: UpdateUserRequest
    ): UserResponse {

        return userService.updateUser(id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(
        @PathVariable id: Long
    ) {

        userService.deleteUser(id)
    }
}