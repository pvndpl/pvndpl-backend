package ru.pvndpl.repository

import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.pvndpl.entity.Chat
import ru.pvndpl.entity.ChatType
import ru.pvndpl.model.ChatDto
import java.util.*

private val logger = KotlinLogging.logger {}

@Slf4j
@Repository
class ChatRepository(
    private val jdbcTemplate: JdbcTemplate,
) {

    fun createChat(
        type: String,
        title: String,
        userId: UUID
    ): UUID {

        val uuid: UUID = UUID.randomUUID()
        val typeId = findChatTypeByTitle(type)?.id

        val queryToCreateChat = "insert into chats(id, title, type_id) " +
                "values (\'$uuid', \'$title', \'$typeId')"

        logger.warn { queryToCreateChat }

        jdbcTemplate.update(queryToCreateChat)

        addMemberToChat(uuid, userId)

        return uuid
    }

    fun addMemberToChat(chatId: UUID, userId: UUID) {
        val queryAddMemberToChat = "insert into chats_participant(chat_id, user_id) " +
                "values (\'$chatId', \'$userId')"

        logger.warn { queryAddMemberToChat }

        jdbcTemplate.update(queryAddMemberToChat)
    }

    fun findChatTypeByTitle(type: String?): ChatType? {
        return jdbcTemplate.queryForObject(
            "select * from chats_types where type like \'$type' limit 1",
            ROW_MAPPER_CHATS_TYPES
        )
    }

    fun getAllUserChats(userId: UUID): List<ChatDto> {
        return jdbcTemplate.query(
            "SELECT c.id, u.id, u.first_name, u.second_name\n" +
                    "FROM (SELECT c.id\n" +
                    "      FROM chats c\n" +
                    "               JOIN chats_types ct ON ct.id = c.type_id\n" +
                    "               JOIN chats_participant cp ON c.id = cp.chat_id\n" +
                    "      WHERE cp.user_id = \'$userId') AS c\n" +
                    "         JOIN chats_participant cp ON c.id = cp.chat_id\n" +
                    "         JOIN users u ON u.id = cp.user_id\n" +
                    "WHERE cp.user_id != \'$userId'",
            ROW_MAPPER_CHAT_DTO
        )
    }

    fun hasChat(userId: UUID, userInvitedId: UUID): Boolean {

        val query: Integer? = jdbcTemplate.queryForObject(
            "SELECT max(res.num)\n" +
                    "FROM (SELECT count(user_id) num\n" +
                    "    FROM chats\n" +
                    "         JOIN chats_participant cp on chats.id = cp.chat_id\n" +
                    "WHERE cp.user_id = \'$userId'\n" +
                    "   OR cp.user_id = \'$userInvitedId'\n" +
                    "GROUP BY chat_id) AS res",
            Integer::class.java
        )

        return query != null && query.equals(2)
    }

    private companion object {
        val ROW_MAPPER_CHAT = RowMapper<Chat> { rs, _ ->
            Chat(
                rs.getObject("id", UUID::class.java),
                rs.getObject("typeId", UUID::class.java),
                rs.getString("title")
            )
        }

        val ROW_MAPPER_CHAT_DTO = RowMapper<ChatDto> { rs, _ ->
            ChatDto(
                rs.getObject("id", UUID::class.java),
                rs.getObject("id", UUID::class.java),
                rs.getString("first_name"),
                rs.getString("second_name")
            )
        }

        val ROW_MAPPER_CHATS_TYPES = RowMapper<ChatType> { rs, _ ->
            ChatType(
                rs.getObject("id", UUID::class.java),
                rs.getString("type")
            )
        }
    }
}