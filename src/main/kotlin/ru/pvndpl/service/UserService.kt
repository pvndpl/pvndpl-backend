package ru.pvndpl.service

import org.springframework.stereotype.Service
import ru.pvndpl.entity.User
import ru.pvndpl.repository.UserRepository

@Service
class UserService(
    val userRepository: UserRepository
) {


    fun fetchUsers(): List<User>? {

        return userRepository.fetchUsers()
    }
}