package com.charlie.spring_course.infrastructure.persistence.mongo.adapter

import com.charlie.spring_course.domain.models.Note
import com.charlie.spring_course.domain.ports.outgoing.NoteRepository
import com.charlie.spring_course.infrastructure.persistence.mongo.mapper.toDomainModel
import com.charlie.spring_course.infrastructure.persistence.mongo.mapper.toEntity
import com.charlie.spring_course.infrastructure.persistence.mongo.repository.MongoNoteRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class NoteRepositoryAdapter(
    private val mongoNoteRepository: MongoNoteRepository
) : NoteRepository {
    override fun save(note: Note): Note {
        val saved = mongoNoteRepository.save(note.toEntity())
        return saved.toDomainModel()
    }

    override fun findByOwnerId(ownerId: String): List<Note> {
        val ownerObjectId = ObjectId(ownerId)
        return mongoNoteRepository.findByOwnerId(ownerObjectId)
            .map { it.toDomainModel() }
    }

    override fun deleteById(id: String) {
        val objectId = ObjectId(id)
        mongoNoteRepository.deleteById(objectId)
    }

    override fun findById(id: String): Note? {
        val objectId = ObjectId(id)
        return mongoNoteRepository.findById(objectId).orElse(null)?.toDomainModel()
    }

}