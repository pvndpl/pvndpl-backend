package ru.pvndpl.model

import lombok.NonNull

data class RegistrationDto (

    @NonNull
    val username: String,

    @NonNull
    val password: String,

    @NonNull
    val email: String,

    @NonNull
    val firstName: String,

    @NonNull
    val secondName: String
)