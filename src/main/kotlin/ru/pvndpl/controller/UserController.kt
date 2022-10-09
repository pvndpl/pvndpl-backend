package ru.pvndpl.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.pvndpl.entity.User
import ru.pvndpl.service.UserService

@RestController
class UserController constructor(
        var userService: UserService
) {

    @GetMapping("/users")
    fun findUser(): List<User>? {

        return userService.fetchUsers()
    }
}