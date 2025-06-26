package com.charlie.spring_course.infrastructure.persistence.mongo.repository

import com.charlie.spring_course.infrastructure.persistence.mongo.entity.UserEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoUserRepository : MongoRepository<UserEntity, ObjectId> {
    fun findByEmail(email: String): UserEntity?
}