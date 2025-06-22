package com.charlie.spring_course.database.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(
    @Id val id:ObjectId = ObjectId.get(),
    val password: String,
    val email: String,
)
