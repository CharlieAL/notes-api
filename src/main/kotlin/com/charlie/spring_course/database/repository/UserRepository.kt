package com.charlie.spring_course.database.repository

import com.charlie.spring_course.database.models.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository


interface UserRepository : MongoRepository<User, ObjectId> {
    fun findByEmail(email: String): User?
}