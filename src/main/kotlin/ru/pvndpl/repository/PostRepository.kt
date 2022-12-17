package ru.pvndpl.repository

import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.pvndpl.entity.Post
import java.util.*
import kotlin.collections.List

private val logger = KotlinLogging.logger {}

@Slf4j
@Repository
class PostRepository (private val jdbcTemplate: JdbcTemplate){
    fun createPost(creatorId : UUID, category : String, content : String) : UUID {

        val uuid : UUID = UUID.randomUUID()

        val query = "insert into posts(id, creator_id, category, content)" +
                "values(\'$uuid', \'$creatorId', \'$category', \'$content')"

        logger.warn { query }

        jdbcTemplate.update(query)

        return uuid
    }

    fun getAllPosts(userId: UUID) : List<Post>{

        return jdbcTemplate.query("SELECT *\n" +
                "FROM posts\n" +
                "WHERE creator_id IN (SELECT subscribers_id\n" +
                "                     FROM users_subscribers\n" +
                "                     WHERE user_id = \'$userId')", ROW_MAPPER_POSTS)
    }

    fun getUserPosts(id : UUID) : List<Post>{

        return jdbcTemplate.query("select * from posts where creator_id like \'$id'", ROW_MAPPER_POSTS)
    }

    private companion object {

        val ROW_MAPPER_POSTS = RowMapper<Post> { rs, _ ->
            Post(
                rs.getObject("id", UUID::class.java),
                rs.getObject("creator_id", UUID::class.java),
                rs.getString("category"),
                rs.getString("content"),
            )
        }
    }
}