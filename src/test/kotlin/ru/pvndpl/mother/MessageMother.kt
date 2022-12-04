package ru.pvndpl.mother

import ru.pvndpl.entity.Message
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class MessageMother {

    companion object {
        val ID: UUID = UUID.randomUUID()
        val USER_ID_FROM: UUID = UUID.randomUUID()
        val TIME: Timestamp = Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC))
        val TEXT: String = "xyz"

        fun message(): Message {
            return Message(ID, USER_ID_FROM, TIME, TEXT)
        }
    }
}