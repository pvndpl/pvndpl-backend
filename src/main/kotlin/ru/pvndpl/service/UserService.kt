package ru.pvndpl.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.pvndpl.model.RegistrationDto
import ru.pvndpl.model.SimpleUserAuthInfo
import ru.pvndpl.repository.UserRepository
import java.util.*

@Service
class UserService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder
) {

    fun createUser(registrationDto: RegistrationDto): UUID {

        return userRepository.createUser(
            registrationDto.username,
            passwordEncoder.encode(registrationDto.password),
            registrationDto.email,
            registrationDto.firstName,
            registrationDto.secondName
        )
    }

    fun findByUsername(username: String): SimpleUserAuthInfo? {

        return userRepository.findByUsername(username)
    }
}