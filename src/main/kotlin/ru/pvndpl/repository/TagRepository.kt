package ru.pvndpl.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.pvndpl.model.TagDto
import ru.pvndpl.model.UserDto
import java.util.*

@Repository
class TagRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun createTag(name: String, sysname: String): TagDto {

        val id: UUID = UUID.randomUUID()

        jdbcTemplate.update("insert into tags(id, name, sysname) values ('$id', '$name', '$sysname')")

        return TagDto(id, name, sysname)
    }

    fun fetchTags(userId: UUID): List<TagDto> {

        return jdbcTemplate.query(
            "select t.id, t.name, t.sysname from user2tag u2t join tags t on u2t.tagid = t.id where u2t.userid = '$userId'",
            ROW_MAPPER_TAG_DTO
        )
    }

    fun fetchTags(): List<TagDto> {

        return jdbcTemplate.query(
            "select id, name, sysname from tags",
            ROW_MAPPER_TAG_DTO
        )
    }

    fun findTagIdBySysname(tagSysname: String): UUID? {

        return jdbcTemplate.queryForObject(
            "select id from tags where sysname like '$tagSysname'",
            UUID::class.java
        )
    }

    fun createUserTag(userId: UUID, tagId: UUID) {

        jdbcTemplate.update("insert into user2tag (userid, tagid) values ('$userId', '$tagId')")
    }

    fun getAllUsersByTagName(tagName: String): List<UserDto> {

        return jdbcTemplate.query(
            "SELECT u.id, u.username, u.first_name, u.second_name\n" +
                    "FROM user2tag ut\n" +
                    "     JOIN tags t on t.id = ut.tagid AND t.sysname LIKE \'$tagName'\n" +
                    "JOIN users u on ut.userid = u.id",
            ROW_MAPPER_USER_DTO
        )
    }

    private companion object {
        val ROW_MAPPER_TAG_DTO = RowMapper<TagDto> { rs, _ ->
            TagDto(
                rs.getObject("id", UUID::class.java),
                rs.getString("name"),
                rs.getString("sysname")
            )
        }
        val ROW_MAPPER_USER_DTO = RowMapper<UserDto> { rs, _ ->
            UserDto(
                rs.getObject("id", UUID::class.java),
                rs.getString("username"),
                rs.getString("first_name"),
                rs.getString("second_name")
            )
        }
    }

}