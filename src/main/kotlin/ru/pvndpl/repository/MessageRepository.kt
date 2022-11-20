package ru.pvndpl.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.pvndpl.entity.Message
import java.util.UUID

@Repository
class MessageRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {

    fun fetchUserMessages(id: UUID): List<Message> {
        return jdbcTemplate.query(
            "select m.id, m.sending_time, um.user_id_from, um.user_id_to, m.text\n" +
                    "from messages m\n" +
                    "         join user_message um on m.id = um.message_id\n" +
                    "where uuid_eq(user_id_to, \'$id') OR uuid_eq(um.user_id_from, \'$id')\n" +
                    "order by m.sending_time",
            ROW_MAPPER_MESSAGES
        )
    }

    private companion object {
        val ROW_MAPPER_MESSAGES = RowMapper<Message> { rs, _ ->
            Message(
                rs.getInt("id"),
                rs.getTimestamp("sending_time"),
                rs.getString("text")
            )
        }
    }
}