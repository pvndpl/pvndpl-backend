package ru.pvndpl.mother

import ru.pvndpl.entity.Profile
import java.util.*

class ProfileParent1 {
    companion object {
        val userId: UUID = UUID.randomUUID()
        val postsCount: Int = 0
        val subscribersCount: Int = 0
        val commentsCount: Int = 0
        val about: String = "Текст обо мне..."
        val createdDate: Date = Date()
        val city: String = "Москва"
        val website: String = "http://localhost:3000/pvndpl-front/newsfeed"
        val tvShows: String = "+100500"
        val showmen: String = "Зеленский"
        val books: String = "Путь самца"
        val games: String = "Lego"
        val email: String = "mail@mail.mail"
        val birthdate: Date = Date()
        val busyness: String = "Делать деньги"
        val nativeCity: String = "Крыжополь"

        fun message(): Profile {
            return Profile(
                ProfileParent1.userId,
                ProfileParent1.postsCount,
                ProfileParent1.subscribersCount,
                ProfileParent1.commentsCount,
                ProfileParent1.about,
                ProfileParent1.createdDate,
                ProfileParent1.city,
                ProfileParent1.website,
                ProfileParent1.tvShows,
                ProfileParent1.showmen,
                ProfileParent1.books,
                ProfileParent1.games,
                ProfileParent1.email,
                ProfileParent1.birthdate,
                ProfileParent1.busyness,
                ProfileParent1.nativeCity,
                )
        }
    }
}