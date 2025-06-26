package com.charlie.spring_course.domain.ports.outgoing

interface JwtService {
    fun generateAccessToken(userId: String): String
    fun generateRefreshToken(userId: String): String
    fun validateAccessToken(token: String): Boolean
    fun validateRefreshToken(token: String): Boolean
    fun getUserIdFromToken(token: String): String
}