package ru.pvndpl.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtTokenUtil(
    @Value("\${jwt.secret}")
    private var secret: String
) {

    companion object {
        var JWT_TOKEN_VALIDITY: Long = 5 * 60 * 60;
    }

    fun getUsernameFromToken(token: String): String {
        return getAllClaimsFromToken(token).subject
    }

    fun getExpirationToken(token: String): Date {
        return getAllClaimsFromToken(token).expiration
    }

    private fun getAllClaimsFromToken(token: String): Claims {

        return Jwts
            .parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String): Boolean {

        val expiration: Date = getExpirationToken(token)

        return expiration.before(Date())
    }

    fun generateToken(userDetails: UserDetails): String {

        val claims: Map<String, Any?> = HashMap()

        return doGenerateToken(claims, userDetails.username)
    }


    private fun doGenerateToken(claims: Map<String, Any?>, subject: String): String {

        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean? {

        val username: String = getUsernameFromToken(token)

        return username == userDetails.username && !isTokenExpired(token)
    }
}