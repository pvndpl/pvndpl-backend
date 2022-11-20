package ru.pvndpl.entity

import java.sql.Timestamp
import java.util.UUID

data class Message(
    val id: Int,
    val sendingTime: Timestamp,
    val text: String
)
