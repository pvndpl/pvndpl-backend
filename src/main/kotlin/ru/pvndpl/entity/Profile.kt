package ru.pvndpl.entity

import java.util.Date
import java.util.UUID

data class Profile(
    val userId: UUID,
    val postsCount: Int,
    val subscribersCount: Int,
    val subscriptionsCount: Int,
    val about: String?,
    val createdDate: Date,
    val city: String?,
    val website: String?,
    val tvShows: String?,
    val movies: String?,
    val showmen: String?,
    val books: String?,
    val games: String?,
    val email: String,
    val birthdate: Date?,
    val busyness: String?,
    val nativeCity: String?,
)
