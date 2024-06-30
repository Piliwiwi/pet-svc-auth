package com.example.auth.services


import com.example.auth.repositories.UserRepository
import org.springframework.stereotype.Service


@Service
class AuthService(private val repository: UserRepository) {
    fun isUsernameUnique(username: String): Boolean {
        return repository.findByUsername(username) == null
    }
    fun isEmailUnique(email: String): Boolean {
        return repository.findByEmail(email) == null
    }
}