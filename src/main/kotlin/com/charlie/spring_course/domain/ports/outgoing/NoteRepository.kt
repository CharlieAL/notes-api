package com.charlie.spring_course.domain.ports.outgoing

import com.charlie.spring_course.domain.models.Note

interface NoteRepository {
    fun save(note: Note): Note
    fun findByOwnerId(ownerId: String): List<Note>
    fun deleteById(id: String)
    fun findById(id: String): Note?
}