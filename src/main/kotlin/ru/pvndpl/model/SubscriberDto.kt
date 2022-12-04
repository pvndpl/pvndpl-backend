package ru.pvndpl.model

import java.util.*

data class SubscriberDto(
    var id: UUID,
    var username: String,
    var firstName: String,
    var secondName: String
)
