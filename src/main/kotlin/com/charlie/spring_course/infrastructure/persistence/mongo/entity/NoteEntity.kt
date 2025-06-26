package com.charlie.spring_course.infrastructure.persistence.mongo.entity

import com.charlie.spring_course.domain.models.Note
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("notes")
data class NoteEntity(
    val title: String,
    val content: String,
    val color: Long,
    val createdAt: Instant = Instant.now(),
    val ownerId: ObjectId,
    @Id val id: ObjectId = ObjectId.get()
)



