package ru.pvndpl.mother

import ru.pvndpl.entity.Post
import java.util.*

class PostMother {
    companion object{
        val id : UUID = UUID.randomUUID()
        val creatorId : UUID = UUID.randomUUID()
        val category : String = "World"
        val content : String = "asdadasds"

        fun post() : Post{
            return Post(id, creatorId, category, content)
        }
    }
}