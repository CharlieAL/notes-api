package com.charlie.spring_course.infrastructure.persistence.mongo.repository

import com.charlie.spring_course.infrastructure.persistence.mongo.entity.RefreshTokenEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoRefreshTokenRepository : MongoRepository<RefreshTokenEntity, ObjectId> {
    fun findByUserIdAndHashedToken(userId: ObjectId, hashedToken: String): RefreshTokenEntity?

    fun deleteByUserIdAndHashedToken(userId: ObjectId, hashedToken: String)
}