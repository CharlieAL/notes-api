package com.charlie.spring_course.infrastructure.persistence.mongo.repository

import com.charlie.spring_course.infrastructure.persistence.mongo.entity.NoteEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoNoteRepository : MongoRepository<NoteEntity, ObjectId> {
    fun findByOwnerId(ownerId: ObjectId): List<NoteEntity>
}