package com.charlie.spring_course.domain.ports.outgoing

import com.charlie.spring_course.domain.models.User

interface UserRepository {
    fun findByEmail(email: String): User?
    fun save(user: User): User
    fun findById(id: String): User?
}