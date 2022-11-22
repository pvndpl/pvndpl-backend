package ru.pvndpl.repository

import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.pvndpl.entity.Message
import java.sql.Timestamp
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Slf4j
@Repository
class MessageRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun sendMessage(userId: UUID, time: Timestamp, text: String, chatId: UUID) : UUID {

        val uuid: UUID = UUID.randomUUID()

        val queryToSendMessage = "insert into messages(id, user_id_from, time, text) " +
                "values (\'$uuid', \'$userId', \'$time', '$text')"

        logger.warn { queryToSendMessage }

        jdbcTemplate.update(queryToSendMessage)

        val query = "INSERT INTO chats_messages(chats_id, message_id)\n" +
                "VALUES (\'$chatId', \'$uuid')"

        logger.warn { query }

        jdbcTemplate.update(query)

        return uuid
    }

    fun getAllMessagesByChatId(chatId: UUID): List<Message> {
        return jdbcTemplate.query(
            "SELECT *\n" +
                    "FROM messages m\n" +
                    "JOIN chats_messages cm on m.id = cm.message_id\n" +
                    "WHERE cm.chats_id = \'$chatId'\n",
            ROW_MAPPER_MESSAGES
        )
    }

    fun getMessageById(messageId: UUID): Message? {
        return jdbcTemplate.queryForObject(
            "SELECT *\n" +
                    "FROM messages\n" +
                    "WHERE id = \'$messageId'",
            ROW_MAPPER_MESSAGES
        )
    }

    private companion object {
        val ROW_MAPPER_MESSAGES = RowMapper<Message> { rs, _ ->
            Message(
                rs.getObject("id", UUID::class.java),
                rs.getObject("user_id_from", UUID::class.java),
                rs.getTimestamp("time"),
                rs.getString("text")
            )
        }
    }
}