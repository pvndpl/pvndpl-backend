package ru.pvndpl.model

import java.util.*

data class ChatDto(
    val chatId: UUID,
    val userId: UUID,
    val userFirstName: String,
    val userLastName: String
)
