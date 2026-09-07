package com.example.userservice.service

import com.example.userservice.dto.CreateUserRequest
import com.example.userservice.dto.UpdateUserRequest
import com.example.userservice.dto.UserResponse
import com.example.userservice.entity.User
import com.example.userservice.exceptions.DuplicateEmailException
import com.example.userservice.exceptions.UserNotFoundException
import com.example.userservice.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser(request: CreateUserRequest): UserResponse {

        if (userRepository.existsByEmail(request.email)) {
            throw DuplicateEmailException(
                "User with email ${request.email} already exists"
            )
        }

        val user = User(
            name = request.name,
            email = request.email
        )

        val savedUser = userRepository.save(user)

        return savedUser.toResponse()
    }

    fun getAllUsers(): List<UserResponse> {

        return userRepository.findAll()
            .map { it.toResponse() }
    }

    fun getUserById(id: Long): UserResponse {

        val user = userRepository.findById(id)
            .orElseThrow {
                UserNotFoundException(
                    "User with id $id not found"
                )
            }

        return user.toResponse()
    }

    fun updateUser(
        id: Long,
        request: UpdateUserRequest
    ): UserResponse {

        val user = userRepository.findById(id)
            .orElseThrow {
                UserNotFoundException(
                    "User with id $id not found"
                )
            }

        if (user.email != request.email &&
            userRepository.existsByEmail(request.email)
        ) {
            throw DuplicateEmailException(
                "User with email ${request.email} already exists"
            )
        }

        user.name = request.name
        user.email = request.email

        val updatedUser = userRepository.save(user)

        return updatedUser.toResponse()
    }

    fun deleteUser(id: Long) {

        if (!userRepository.existsById(id)) {
            throw UserNotFoundException(
                "User with id $id not found"
            )
        }

        userRepository.deleteById(id)
    }

    private fun User.toResponse(): UserResponse {
        return UserResponse(
            id = id,
            name = name,
            email = email
        )
    }
}