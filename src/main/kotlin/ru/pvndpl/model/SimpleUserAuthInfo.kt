package ru.pvndpl.model

import java.util.*

data class SimpleUserAuthInfo (
    var id: UUID,
    var username: String,
    var password: String,
)