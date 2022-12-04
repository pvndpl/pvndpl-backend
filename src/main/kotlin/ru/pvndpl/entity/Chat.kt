package ru.pvndpl.entity

import java.util.*

data class Chat(
    val id: UUID,
    val typeId: UUID,
    val title: String,
)