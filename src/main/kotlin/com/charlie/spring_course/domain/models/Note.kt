package com.charlie.spring_course.domain.models

import java.time.Instant

data class Note(
    val id: String,
    val ownerId: String,
    val title: String,
    val content: String,
    val color: Long,
    val createdAt: Instant = Instant.now(),
)
