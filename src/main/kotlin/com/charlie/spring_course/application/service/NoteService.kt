package com.charlie.spring_course.application.service

import com.charlie.spring_course.domain.models.Note
import com.charlie.spring_course.domain.ports.incoming.NoteUseCase
import com.charlie.spring_course.domain.ports.outgoing.NoteRepository
import org.springframework.stereotype.Service

@Service
class NoteService(
    private val noteRepository: NoteRepository
) : NoteUseCase {
    override fun createNote(
        title: String,
        content: String,
        ownerId: String
    ): Note {
        val note = Note(
            title = title,
            content = content,
            ownerId = ownerId,
            color = 0L,
            id = "" // ID will be generated in the repository
        )

        return noteRepository.save(note)
    }

    override fun getUserNotes(ownerId: String): List<Note> {
        return noteRepository.findByOwnerId(ownerId)
    }

    override fun updateNote(
        id: String,
        title: String,
        content: String,
        ownerId: String
    ): Note {
        val existingNote = noteRepository.findById(id)
            ?: throw IllegalArgumentException("Note with ID $id not found")

        if (existingNote.ownerId != ownerId) {
            throw IllegalArgumentException("You do not have permission to update this note")
        }

        val updatedNote = existingNote.copy(
            title = title,
            content = content
        )

        return noteRepository.save(updatedNote)

    }

    override fun deleteNote(id: String, ownerId: String) {
        val existingNote = noteRepository.findById(id)
            ?: throw IllegalArgumentException("Note with ID $id not found")

        if (existingNote.ownerId != ownerId) {
            throw IllegalArgumentException("You do not have permission to delete this note")
        }

        noteRepository.deleteById(id)
    }
}