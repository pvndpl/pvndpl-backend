package ru.pvndpl.entity

import java.sql.Timestamp
import java.util.*

data class Message(
    val id: UUID,
    val userIdFrom: UUID,
    val time: Timestamp,
    val text: String,
)
