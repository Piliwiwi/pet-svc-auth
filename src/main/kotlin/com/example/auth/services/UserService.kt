package com.example.auth.services

import com.example.auth.dtos.error.UserNotFoundException
import com.example.auth.models.User
import com.example.auth.repositories.UserRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(private val repository: UserRepository) {
    fun save(user: User): User = repository.insert(user)
    fun getByEmail(email: String): User? = repository.findByEmail(email)

    fun getByMongoId(id: ObjectId): User? = repository.findOneById(id)

    fun getById(id: String): Optional<User> = repository.findById(id)

    fun getUsersByIds(userIds: List<String>): List<User> {
        return if (userIds.isEmpty()) {
            emptyList()
        } else {
            repository.findByIdIn(userIds)
        }
    }


    fun addPetToUser(userId: String, petId: String): User? {
        val user = repository.findOneById(ObjectId(userId))

        val updatedPets = user?.pets?.toMutableList()
        updatedPets?.add(petId)

        updatedPets?.let { user.pets = it }
        if (user == null) throw UserNotFoundException("user not found")
        else return repository.save(user)
    }
}