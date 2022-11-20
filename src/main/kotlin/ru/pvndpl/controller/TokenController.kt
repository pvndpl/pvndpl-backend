package ru.pvndpl.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.pvndpl.model.TokenRequestDto
import ru.pvndpl.model.TokenResponseDto
import ru.pvndpl.service.JwtUserDetailsService
import ru.pvndpl.util.JwtTokenUtil


@RestController
class TokenController(
    var authenticationManager: AuthenticationManager,
    var jwtTokenUtil: JwtTokenUtil,
    var userDetailsService: JwtUserDetailsService
) {

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody tokenRequestDto: TokenRequestDto): ResponseEntity<TokenResponseDto> {

        authenticate(tokenRequestDto.username, tokenRequestDto.password)

        val userDetails: UserDetails = userDetailsService
            .loadUserByUsername(tokenRequestDto.username);

        val token: String = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(TokenResponseDto(token))
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {

        try {

            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))

        } catch (e: DisabledException) {

            throw Exception("USER_DISABLED", e)

        } catch (e: BadCredentialsException) {

            throw Exception("INVALID_CREDENTIALS", e)
        }
    }

}