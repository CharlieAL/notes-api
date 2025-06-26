package com.charlie.spring_course.infrastructure.persistence.mongo.adapter

import com.charlie.spring_course.domain.models.User
import com.charlie.spring_course.domain.ports.outgoing.UserRepository
import com.charlie.spring_course.infrastructure.persistence.mongo.mapper.toDomainModel
import com.charlie.spring_course.infrastructure.persistence.mongo.mapper.toEntity
import com.charlie.spring_course.infrastructure.persistence.mongo.repository.MongoUserRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(
    private val mongoUserRepository: MongoUserRepository
) : UserRepository {
    override fun findByEmail(email: String): User? {
        val userEntity = mongoUserRepository.findByEmail(email)
        return userEntity?.toDomainModel() // Convert UserEntity to User domain model
    }

    override fun save(user: User): User {
        val saved = mongoUserRepository.save(user.toEntity())
        return saved.toDomainModel()
    }

    override fun findById(id: String): User? {
        val objectId = ObjectId(id)
        val userEntity = mongoUserRepository.findById(objectId).orElse(null)
        return userEntity?.toDomainModel() // Convert UserEntity to User domain model
    }
}