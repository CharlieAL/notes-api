package com.charlie.spring_course.infrastructure.persistence.mongo.entity

import com.charlie.spring_course.domain.models.User
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class UserEntity(
    @Id val id: ObjectId = ObjectId.get(),
    val password: String,
    val email: String,
)


