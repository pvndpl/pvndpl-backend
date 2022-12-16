package ru.pvndpl.model

import java.util.UUID

data class ChatCreateDto(
    val type: String,
    val title: String,
    val userInvitedID: UUID
)
