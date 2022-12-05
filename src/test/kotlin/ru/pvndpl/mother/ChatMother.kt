package ru.pvndpl.mother

import ru.pvndpl.entity.Chat
import ru.pvndpl.model.ChatDto
import java.util.*

class ChatMother {
    companion object{
        val id : UUID = UUID.randomUUID()
        val type : String = "some type"
        val title : String = "some title"

        fun chat() : ChatDto {
            return ChatDto(id, type, title)
        }
    }
}