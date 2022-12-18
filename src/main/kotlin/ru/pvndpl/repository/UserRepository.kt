package ru.pvndpl.repository

import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.pvndpl.model.SimpleUserAuthInfo
import ru.pvndpl.model.SimpleUserInfoDto
import ru.pvndpl.model.UserDto
import java.util.*

private val logger = KotlinLogging.logger {}

@Slf4j
@Repository
class UserRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun findByUsername(username: String?): SimpleUserAuthInfo? {
        return jdbcTemplate.queryForObject(
            "select * from users where username like \'$username' limit 1",
            ROW_MAPPER_SIMPLE_USER_AUTH_INFO
        )
    }

    fun findById(userId: UUID): UserDto? {
        return jdbcTemplate.queryForObject(
            "select * from users where id = \'$userId' limit 1",
            ROW_MAPPER_USER_DTO
        )
    }

    fun createUser(
        username: String,
        password: String,
        email: String,
        firstName: String,
        secondName: String
    ): UUID {

        val uuid: UUID = UUID.randomUUID()

        val query = "insert into users(id, username, password, email, first_name, second_name) " +
                "values (\'$uuid', \'$username', \'$password', \'$email', \'$firstName', \'$secondName')"

        logger.warn { query }

        jdbcTemplate.update(query)

        return uuid
    }

    fun fetchAllByPartOfUsername(username: String, userId: UUID): List<SimpleUserInfoDto> {

        return jdbcTemplate.query(
            "select id, username from users where username ilike '%$username%' and id != '$userId'",
            ROW_MAPPER_SIMPLE_USER_INFO_DTO
        )
    }

    private companion object {
        val ROW_MAPPER_SIMPLE_USER_AUTH_INFO = RowMapper<SimpleUserAuthInfo> { rs, _ ->
            SimpleUserAuthInfo(
                rs.getObject("id", UUID::class.java),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("first_name"),
                rs.getString("second_name")
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
        val ROW_MAPPER_SIMPLE_USER_INFO_DTO = RowMapper<SimpleUserInfoDto> { rs, _ ->
            SimpleUserInfoDto(
                rs.getObject("id", UUID::class.java),
                rs.getString("username")
            )
        }
    }
}