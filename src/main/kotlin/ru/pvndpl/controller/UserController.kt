package ru.pvndpl.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.pvndpl.model.RegistrationDto
import ru.pvndpl.model.SimpleUserAuthInfo
import ru.pvndpl.service.UserService

@RestController
class UserController constructor(
    val userService: UserService
) {

    @PostMapping("/users")
    fun crateUser(@RequestBody registrationDto: RegistrationDto): ResponseEntity<Void> {

        userService.createUser(registrationDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @GetMapping("/user-info")
    fun getUserInf(auth: Authentication): ResponseEntity<SimpleUserAuthInfo> {

        return ResponseEntity.ok(userService.findByUsername(auth.name));
    }
}