package com.charlie.spring_course.infrastructure.security

import com.charlie.spring_course.domain.ports.outgoing.HashEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class HashEncoder : HashEncoder {
    private val bcrypt = BCryptPasswordEncoder()

    override fun encode(raw: String): String = bcrypt.encode(raw)

    override fun matches(raw: String, encoded: String): Boolean = bcrypt.matches(raw, encoded)
}