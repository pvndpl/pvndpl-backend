package ru.pvndpl.model

import java.util.*

data class UserDto(
    var id: UUID,
    var username: String,
    val firstname: String,
    val lastname: String
)
