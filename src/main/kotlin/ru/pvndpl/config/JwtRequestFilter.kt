﻿package ru.pvndpl.config

import io.jsonwebtoken.ExpiredJwtException
import lombok.extern.slf4j.Slf4j
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.pvndpl.service.JwtUserDetailsService
import ru.pvndpl.util.JwtTokenUtil
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
class JwtRequestFilter(
    var jwtUserDetailsService: JwtUserDetailsService,
    var jwtTokenUtil: JwtTokenUtil
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {

        val requestTokenHeader = request.getHeader("Authorization")

        var username: String? = null
        var jwtToken: String? = null

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

            jwtToken = requestTokenHeader.substring(7)

//            try {

                username = jwtTokenUtil.getUsernameFromToken(jwtToken)

//            } catch (e: IllegalArgumentException) {
//
//                println("Unable to get JWT Token")
//
//            } catch (e: ExpiredJwtException) {
//
//                println("JWT Token has expired")
//            }

        } else {

            logger.warn("JWT Token does not begin with Bearer String")
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {

            val userDetails: UserDetails = jwtUserDetailsService.loadUserByUsername(username)

//            if (jwtTokenUtil.validateToken(jwtToken!!, userDetails)!!) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )

                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
//            }
        }

        chain.doFilter(request, response)
    }
}