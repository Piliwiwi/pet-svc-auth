package com.example.auth.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Document("users")
class User {
    @MongoId
    @JsonIgnore
    var id: ObjectId = ObjectId.get()
    var name: String = ""
    var email: String = ""
    var token: String = ""
    var username: String = ""
    @JsonIgnore
    var password: String = ""
        set(value) {
            val encoder = BCryptPasswordEncoder()
            field = encoder.encode(value)
        }

    var pets: List<String> = emptyList()

    fun comparePassword(incomingPassword: String) =
            BCryptPasswordEncoder().matches(incomingPassword, password)
}
