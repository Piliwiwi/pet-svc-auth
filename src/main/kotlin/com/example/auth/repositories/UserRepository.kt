package com.example.auth.repositories

import com.example.auth.models.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface UserRepository : MongoRepository<User, String> {
    @Query("{ 'email' : ?0 }")
    fun findByEmail(email: String): User?
    fun findOneById(id: ObjectId): User?

    @Query("{ 'username' : ?0 }")
    fun findByUsername(username: String): User?

    fun findByIdIn(userIds: List<String>): List<User>
}