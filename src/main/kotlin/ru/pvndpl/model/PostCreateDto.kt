package ru.pvndpl.model

import java.util.*

data class PostCreateDto (
    val creatorId : UUID,
    val category : String,
    val content : String,
        )