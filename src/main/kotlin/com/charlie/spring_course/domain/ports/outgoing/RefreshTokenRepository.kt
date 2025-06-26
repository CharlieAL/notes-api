package com.charlie.spring_course.domain.ports.outgoing

import com.charlie.spring_course.domain.models.RefreshToken
import org.bson.types.ObjectId

interface RefreshTokenRepository {

    fun findByUserIdAndHashedToken(userId: String, hashedToken: String): RefreshToken?

    fun deleteByUserIdAndHashedToken(userId: String, hashedToken: String)

    fun save(refreshToken: RefreshToken): RefreshToken
}