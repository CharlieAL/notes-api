package com.charlie.spring_course.infrastructure.persistence.mongo.mapper

import com.charlie.spring_course.domain.models.Note
import com.charlie.spring_course.infrastructure.persistence.mongo.entity.NoteEntity
import org.bson.types.ObjectId
import java.time.Instant

fun NoteEntity.toDomainModel(): Note {
    return Note(
        id = id.toHexString(),
        ownerId = ownerId.toHexString(),
        title = title,
        content = content,
        color = color,
        createdAt = createdAt
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = if (this.id.isBlank()) ObjectId.get() else ObjectId(this.id),
        ownerId = ObjectId(this.ownerId),
        title = title,
        content = content,
        color = color,
        createdAt = Instant.now()
    )
}