package ru.pvndpl.entity

import java.util.*

class User(
    var id: UUID,
    var username: String,
    var password: String,
    var email: String,
    var firstName: String,
    var secondName: String
)