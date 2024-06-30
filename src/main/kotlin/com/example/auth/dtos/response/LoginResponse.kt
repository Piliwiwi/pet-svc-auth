package com.example.auth.dtos.response

import com.example.auth.models.User

data class LoginResponse(
        val user: User,
        val token: String
)
