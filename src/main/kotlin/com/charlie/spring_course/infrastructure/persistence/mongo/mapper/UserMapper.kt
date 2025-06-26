package com.charlie.spring_course.infrastructure.persistence.mongo.mapper

import com.charlie.spring_course.domain.models.User
import com.charlie.spring_course.infrastructure.persistence.mongo.entity.UserEntity
import org.bson.types.ObjectId

fun UserEntity.toDomainModel(): User {
    return User(
        id = id.toHexString(),
        password = password,
        email = email
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = if (this.id.isBlank()) ObjectId.get() else ObjectId(this.id),
        password = password,
        email = email
    )
}