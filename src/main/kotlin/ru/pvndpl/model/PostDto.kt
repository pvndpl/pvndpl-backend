package ru.pvndpl.model

import java.util.*

data class PostDto(
    val id : UUID,
    val creatorId : UUID,
    val category : String,
    val content : String,
)