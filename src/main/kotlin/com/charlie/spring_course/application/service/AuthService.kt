package com.charlie.spring_course.application.service

import com.charlie.spring_course.domain.models.RefreshToken
import com.charlie.spring_course.domain.models.TokenPair
import com.charlie.spring_course.domain.models.User
import com.charlie.spring_course.domain.ports.incoming.AuthUseCase
import com.charlie.spring_course.domain.ports.outgoing.RefreshTokenRepository
import com.charlie.spring_course.domain.ports.outgoing.UserRepository
import com.charlie.spring_course.infrastructure.security.HashEncoder
import com.charlie.spring_course.infrastructure.security.JwtServiceImpl
import org.springframework.http.HttpStatusCode
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.security.MessageDigest
import java.time.Instant
import java.util.Base64

@Service
class AuthService(
    private val jwtServiceImpl: JwtServiceImpl,
    private val userRepository: UserRepository,
    private val hashEncoder: HashEncoder,
    private val refreshTokenRepository: RefreshTokenRepository
) : AuthUseCase {

    override fun login(email: String, password: String): TokenPair {
        val user = userRepository.findByEmail(email) ?: throw BadCredentialsException("Invalid credentials")
        if (!hashEncoder.matches(password, user.password)) {
            throw BadCredentialsException("Invalid credentials")
        }

        val newAccessToken = jwtServiceImpl.generateAccessToken(user.id)
        val newRefreshToken = jwtServiceImpl.generateRefreshToken(user.id)

        storeRefreshToken(user.id, newRefreshToken)

        return TokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    override fun logout(token: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun register(email: String, password: String): User {
        val user = userRepository.findByEmail(email.trim())
        if (user != null) {
            throw ResponseStatusException(HttpStatusCode.valueOf(409), "User with this email already exists")
        }

        return userRepository.save(
            User(
                id = "",
                email = email,
                password = hashEncoder.encode(password)
            )
        )

    }

    @Transactional
    override fun refreshToken(token: String): TokenPair {
        if (!jwtServiceImpl.validateRefreshToken(token)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid refresh token")
        }
        val userId = jwtServiceImpl.getUserIdFromToken(token)

        val user = userRepository.findById(userId)
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(401), "User not found")

        val hashed = hashToken(token)
        refreshTokenRepository.findByUserIdAndHashedToken(user.id, hashed)
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(401), "Refresh token not recognized")

        refreshTokenRepository.deleteByUserIdAndHashedToken(user.id, hashed)

        val newAccessToken = jwtServiceImpl.generateAccessToken(userId)
        val newRefreshToken = jwtServiceImpl.generateRefreshToken(userId)

        storeRefreshToken(user.id, newRefreshToken)

        return TokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    private fun storeRefreshToken(userId: String, rawRefreshToken: String) {
        val hashed = hashToken(rawRefreshToken)
        val expiryMs = jwtServiceImpl.refreshTokenValidityMs
        val expiresAt = Instant.now().plusMillis(expiryMs)
        refreshTokenRepository.save(
            RefreshToken(
                userId = userId,
                expiresAt = expiresAt,
                hashedToken = hashed
            )
        )

    }

    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashedBytes)
    }
}