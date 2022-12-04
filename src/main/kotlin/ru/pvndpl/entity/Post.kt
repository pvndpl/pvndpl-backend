package ru.pvndpl.entity

import java.util.UUID

data class Post (
    val id : UUID,
    val creatorId : UUID,
    val category : String,
    val content : String,
        )