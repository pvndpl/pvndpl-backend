package ru.pvndpl.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.pvndpl.model.RegistrationDto
import ru.pvndpl.model.SimpleUserAuthInfo
import ru.pvndpl.model.SimpleUserInfoDto
import ru.pvndpl.service.UserService

@RestController
class UserController constructor(
    val userService: UserService
) {

    @PostMapping("/users")
    fun createUser(@RequestBody registrationDto: RegistrationDto): ResponseEntity<Void> {

        userService.createUser(registrationDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @GetMapping("/user-info")
    fun getUserInf(auth: Authentication): ResponseEntity<SimpleUserAuthInfo> {

        return ResponseEntity.ok(userService.findByUsername(auth.name));
    }

    @GetMapping("/users")
    fun findUser(@RequestParam username: String, auth: Authentication): ResponseEntity<List<SimpleUserInfoDto>> {

        return ResponseEntity.ok(userService.fetchAllByPartOfUsername(username))
    }
}