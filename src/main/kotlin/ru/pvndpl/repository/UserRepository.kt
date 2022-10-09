package ru.pvndpl.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.pvndpl.entity.User

@Repository
class UserRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {

    fun fetchUsers(): List<User> {
        return jdbcTemplate.query(
            "select * from users order by id",
            ROW_MAPPER
        )
    }

    private companion object {
        val ROW_MAPPER = RowMapper<User> {
            rs, _ -> User(rs.getInt("id"), rs.getString("name"))
        }
    }
}