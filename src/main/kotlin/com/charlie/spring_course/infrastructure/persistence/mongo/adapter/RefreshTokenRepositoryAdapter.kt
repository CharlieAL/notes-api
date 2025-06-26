package com.charlie.spring_course.infrastructure.persistence.mongo.adapter

import com.charlie.spring_course.domain.models.RefreshToken
import com.charlie.spring_course.domain.ports.outgoing.RefreshTokenRepository
import com.charlie.spring_course.infrastructure.persistence.mongo.mapper.toDomainModel
import com.charlie.spring_course.infrastructure.persistence.mongo.mapper.toEntity
import com.charlie.spring_course.infrastructure.persistence.mongo.repository.MongoRefreshTokenRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class RefreshTokenRepositoryAdapter(
    private val mongoRefreshTokenRepository: MongoRefreshTokenRepository
) : RefreshTokenRepository {
    override fun findByUserIdAndHashedToken(
        userId: String,
        hashedToken: String
    ): RefreshToken? {
        val userObjectId = ObjectId(userId) // Convert String userId to ObjectId
        val refreshToken = mongoRefreshTokenRepository.findByUserIdAndHashedToken(userObjectId, hashedToken)
            ?: return null // Return null if no token is found
        return refreshToken.toDomainModel() // Assuming RefreshToken is already in the correct format

    }

    override fun deleteByUserIdAndHashedToken(userId: String, hashedToken: String) {
        val userObjectId = ObjectId(userId) // Convert String userId to ObjectId
        mongoRefreshTokenRepository.deleteByUserIdAndHashedToken(userObjectId, hashedToken)
    }

    override fun save(refreshToken: RefreshToken): RefreshToken {
        val saved = mongoRefreshTokenRepository.save(refreshToken.toEntity())
        return saved.toDomainModel() // Convert the saved entity back to the domain model
    }

}