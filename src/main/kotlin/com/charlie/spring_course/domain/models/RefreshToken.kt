package com.charlie.spring_course.domain.models

import java.time.Instant

data class RefreshToken(
    val userId: String,
    val expiresAt: Instant,
    val hashedToken: String,
    val createdAt: Instant = Instant.now()
)
