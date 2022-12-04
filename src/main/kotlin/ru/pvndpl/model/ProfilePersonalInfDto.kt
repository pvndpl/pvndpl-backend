package ru.pvndpl.model

import java.util.*

data class ProfilePersonalInfDto(
    val email: String,
    val birthdate: Date,
    val busyness: String,
    val nativeCity: String
)
