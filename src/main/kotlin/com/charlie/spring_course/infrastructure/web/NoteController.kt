package com.charlie.spring_course.infrastructure.web

import com.charlie.spring_course.domain.models.Note
import com.charlie.spring_course.domain.ports.incoming.NoteUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notes")
class NoteController(
    private val noteUseCase: NoteUseCase
) {

    data class NoteRequest(
        val title: String,
        val content: String,
        val color: Long,
    )

    private fun currentUserId(): String {
        val auth = SecurityContextHolder.getContext().authentication
        return auth.principal as String
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody body: NoteRequest
    ): Note {
        val ownerId = currentUserId()
        return noteUseCase.createNote(title = body.title, content = body.content, ownerId = ownerId)
    }

    @GetMapping
    fun getAll(): List<Note> {
        return noteUseCase.getUserNotes(ownerId = currentUserId())
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @Valid @RequestBody request: NoteRequest
    ): Note {
        return noteUseCase.updateNote(
            id = id,
            title = request.title,
            content = request.content,
            ownerId = currentUserId()
        )
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) {
        noteUseCase.deleteNote(
            id = id,
            ownerId = currentUserId()
        )
    }
}