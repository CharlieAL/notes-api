package com.charlie.spring_course.domain.ports.incoming

import com.charlie.spring_course.domain.models.TokenPair
import com.charlie.spring_course.domain.models.User
import jakarta.validation.constraints.Email

interface AuthUseCase {
    fun login(email: String, password: String): TokenPair

    fun logout(token: String): Boolean

    fun register(email: String, password: String): User

    fun refreshToken(token: String): TokenPair
}