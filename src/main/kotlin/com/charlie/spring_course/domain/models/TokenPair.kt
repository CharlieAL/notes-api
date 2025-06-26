package com.charlie.spring_course.domain.models

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)