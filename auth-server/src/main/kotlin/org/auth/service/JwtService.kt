package org.auth.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService {

    // Generate a Base64-encoded 256-bit secret for HS256
    private val secret = "YWFhYmJiY2NjZGRkZWVlZmZmZ2dnaGFhYmJiY2NjZGRkZWVlZmZmZ2dnaGFhYmJiYw==" // example only
    private val key: SecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret))

    private val expirationMillis: Long = 60 * 60 * 1000 // 1 hour

    fun generateToken(username: String): String {
        val now = Date()
        val expiry = Date(now.time + expirationMillis)

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key, Jwts.SIG.HS256)
                .compact()
    }

    fun isTokenExpired(token: String): Boolean {
        val expiration = extractClaims(token).expiration
        return expiration.before(Date())
    }

    fun extractClaims(token: String): Claims {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload
    }

    fun extractUsername(token: String): String {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload.subject
    }
}