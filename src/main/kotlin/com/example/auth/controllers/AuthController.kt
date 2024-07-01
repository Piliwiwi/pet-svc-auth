package com.example.auth.controllers

import com.example.auth.config.Keys
import com.example.auth.dtos.error.Message
import com.example.auth.dtos.request.LoginRequest
import com.example.auth.services.AuthService
import com.example.auth.services.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import java.security.PrivateKey
import java.util.Date
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val authService: AuthService,
    private val keys: Keys
) {
    private val privateKey: PrivateKey = keys.getPrivateKey()


    @GetMapping("/validate-new")
    fun validateIfExist(
        @RequestParam(required = false) username: String?,
        @RequestParam(required = false) email: String?
    ): ResponseEntity<Message> {
        if (username.isNullOrEmpty() && email.isNullOrEmpty()) {
            return ResponseEntity.status(403).body(Message("Both username and email needed"))
        }

        if (!email.isNullOrEmpty()) {
            val isEmailUnique = authService.isEmailUnique(email)
            if (!isEmailUnique) {
                return ResponseEntity.status(403).body(Message("Email already exists"))
            }
        }

        if (!username.isNullOrEmpty()) {
            val isUsernameUnique = authService.isUsernameUnique(username)
            if (!isUsernameUnique) {
                return ResponseEntity.status(403).body(Message("Username already exists"))
            }
        }
        return ResponseEntity.ok().body(Message("Passed test"))
    }

    @PostMapping("/login")
    fun userLogin(@RequestBody body: LoginRequest, response: HttpServletResponse): ResponseEntity<Any> {
        return try {
            val user = userService.getByEmail(body.email)
                ?: return ResponseEntity.badRequest().body(Message("User not found!"))

            if (!user.comparePassword(body.password))
                return ResponseEntity.badRequest().body(Message("Wrong Password!"))

            val issuer = user.id.toString()

            val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 15 * 24 * 60 * 60 * 1000)) // 2 week + 1 day
                .signWith(SignatureAlgorithm.RS256, privateKey).compact()

            user.token = jwt

            ResponseEntity.ok(user)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

    @GetMapping("/user")
    fun getUser(
        @RequestHeader("Authorization") authorization: String?,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        val token = authorization?.removePrefix("SecretWord ")?.trim()

        if (token == null || !authorization.startsWith("SecretWord ")) {
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }

        var body: Claims? = null
        try {
            body = Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token).body
        } catch (e: Exception) {
            return ResponseEntity.status(403).body(e.message)
        }
        return ResponseEntity.ok(userService.getById(body.issuer))
    }

    @PostMapping("/validate-token")
    fun validateToken(@RequestHeader("Authorization") authorization: String?): ResponseEntity<String> {
        val token = authorization?.removePrefix("SecretWord ")?.trim()

        if (token == null || !authorization.startsWith("SecretWord ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        var body: Claims? = null
        try {
            body = Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token).body
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val user = userService.getById(body.issuer).get()
        return ResponseEntity.ok(user.id.toHexString())
    }
}