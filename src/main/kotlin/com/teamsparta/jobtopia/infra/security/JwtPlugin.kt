package com.teamsparta.jobtopia.infra.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.sql.Date
import java.time.Duration
import java.time.Instant


@Component
class JwtPlugin {

    companion object {
        const val ISSUER = "jobtopia"
        const val SECRET = "PO5o6c72FN672Fd31967VWbAWq4Ws5aZ"
        const val ACCESS_TOKEN_EXPIRATION_DURATION : Long = 200
    }

    fun validateToken(token: String) : Result<Jws<Claims>>{
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
        }
    }


    fun generateAccessToken(tokenSubject: String, userName: String): String {
       return generateToken(tokenSubject, userName, Duration.ofHours(ACCESS_TOKEN_EXPIRATION_DURATION))
    }

    private fun generateToken(tokenSubject: String, userName: String, tokenExpirationDuration: Duration?): String {
        val claims: Claims = Jwts.claims()
            .add(mapOf("username" to userName))
            .build()


        val key = Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))
        val now = Instant.now()

        return Jwts.builder()
            .subject(tokenSubject)
            .issuer(ISSUER)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(tokenExpirationDuration)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}

