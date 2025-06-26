package com.charlie.spring_course.domain.ports.incoming

import com.charlie.spring_course.domain.models.Note

interface NoteUseCase {
    fun createNote(title: String, content: String, ownerId: String): Note
    fun getUserNotes(ownerId: String): List<Note>
    fun updateNote(id: String, title: String, content: String, ownerId: String): Note
    fun deleteNote(id: String, ownerId: String)
}