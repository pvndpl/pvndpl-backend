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
            "select c.id, ct.type, c.title\n" +
                    "from chats c\n" +
                    "         join chats_types ct on ct.id = c.type_id\n" +
                    "         join chats_participant cp on c.id = cp.chat_id\n" +
                    "where user_id = \'$userId'",
            ROW_MAPPER_CHAT_DTO
        )
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
                rs.getString("type"),
                rs.getString("title")
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