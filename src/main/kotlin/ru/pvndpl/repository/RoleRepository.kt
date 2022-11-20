package ru.pvndpl.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.pvndpl.util.Role
import java.util.UUID

@Repository
class RoleRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {

    fun fetchUserRoles(userId: UUID): List<Role> {

        return jdbcTemplate.query(
            "select r.rolename from user2role u2r join users u on u.id = u2r.userid join roles r on u2r.roleid = r.id",
            ROW_MAPPER_ROLE
        )
    }

    private companion object {

        var ROW_MAPPER_ROLE = RowMapper<Role> {
            rs, _ -> Role.valueOf(rs.getString("rolename"))
        }
    }
}