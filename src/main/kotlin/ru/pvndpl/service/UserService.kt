package ru.pvndpl.service

import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.pvndpl.model.RegistrationDto
import ru.pvndpl.model.SimpleUserAuthInfo
import ru.pvndpl.model.SimpleUserInfoDto
import ru.pvndpl.model.UserDto
import ru.pvndpl.repository.UserRepository
import java.util.*

@Service
class UserService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    @Lazy
    val profileService: ProfileService
) {

    fun createUser(registrationDto: RegistrationDto): UUID {

        val userId: UUID = userRepository.createUser(
            registrationDto.username,
            passwordEncoder.encode(registrationDto.password),
            registrationDto.email,
            registrationDto.firstName,
            registrationDto.secondName
        )

        profileService.createUserProfile(userId, registrationDto.email)

        return userId
    }

    fun findByUsername(username: String): SimpleUserAuthInfo? {

        return userRepository.findByUsername(username)
    }

    fun getUserInfoById(userID: UUID): UserDto? {

        return userRepository.findById(userID)
    }

    fun fetchAllByPartOfUsername(findUsername: String, userUsername: String): List<SimpleUserInfoDto> {

        var userId: UUID = userRepository.findByUsername(userUsername)?.id ?: throw Exception("Произошла ошибка")

        return userRepository.fetchAllByPartOfUsername(findUsername, userId)
    }
}