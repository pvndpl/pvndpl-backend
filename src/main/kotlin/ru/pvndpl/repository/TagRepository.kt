package ru.pvndpl.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.pvndpl.model.TagDto
import java.util.*

@Repository
class TagRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun createTag(name: String, sysname: String): TagDto {

        val id: UUID = UUID.randomUUID();

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

    private companion object {
        val ROW_MAPPER_TAG_DTO = RowMapper<TagDto> { rs, _ ->
            TagDto(
                rs.getObject("id", UUID::class.java),
                rs.getString("name"),
                rs.getString("sysname")
            )
        }
    }
}