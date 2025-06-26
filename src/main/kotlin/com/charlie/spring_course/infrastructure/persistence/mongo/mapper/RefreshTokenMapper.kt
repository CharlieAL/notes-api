package com.charlie.spring_course.infrastructure.persistence.mongo.mapper

import com.charlie.spring_course.domain.models.RefreshToken
import com.charlie.spring_course.infrastructure.persistence.mongo.entity.RefreshTokenEntity
import org.bson.types.ObjectId

fun RefreshTokenEntity.toDomainModel(): RefreshToken {
    return RefreshToken(
        userId = userId.toHexString(),
        expiresAt = expiresAt,
        hashedToken = hashedToken,
        createdAt = createdAt
    )
}

fun RefreshToken.toEntity(): RefreshTokenEntity {
    return RefreshTokenEntity(
        userId = ObjectId(this.userId),
        expiresAt = expiresAt,
        hashedToken = hashedToken,
        createdAt = createdAt
    )
}