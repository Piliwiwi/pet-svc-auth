package com.example.auth.controllers

import com.example.auth.dtos.error.Message
import com.example.auth.models.User
import com.example.auth.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {
    @PostMapping("/add-pet-to-user")
    fun addPetToUser(
        @RequestParam("userId") userId: String,
        @RequestParam("petId") petId: String
    ): ResponseEntity<User> {
        return try {
            val user = userService.addPetToUser(userId, petId)
            ResponseEntity.ok(user)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }
}