package ru.pvndpl.mother

import ru.pvndpl.model.SubscriberDto
import java.util.*

class SubscriberFather {
    companion object{
        val id : UUID = UUID.randomUUID()
        val username : String = "Aboba"
        val firstName : String = "Aboba"
        val secondName : String = "Aboba Aboba"

        fun subscriberDto() : SubscriberDto {
            return SubscriberDto(id, username, firstName, secondName)
        }
    }
}