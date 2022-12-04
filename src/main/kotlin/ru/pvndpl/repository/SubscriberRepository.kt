package ru.pvndpl.repository

import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate

import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.RowMapper
import ru.pvndpl.model.SubscriberDto
import java.util.*

private val logger = KotlinLogging.logger {}

@Slf4j
@Repository
class SubscriberRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun getUserSubscribers(userId: UUID): List<SubscriberDto> {
        return jdbcTemplate.query(
            "SELECT * FROM users_subscribers\n" +
                    "    JOIN users u on u.id = users_subscribers.subscribers_id\n" +
                    "WHERE user_id = \'$userId'",
            ROW_MAPPER_USERS_SUBSCRIBER
        )
    }

    fun setNewSubscriber(userId: UUID, subscriberId: UUID) {
        val query = "INSERT INTO users_subscribers(user_id, subscribers_id) " +
                "VALUES (\'$userId', \'$subscriberId')"

        logger.warn { query }

        jdbcTemplate.update(query)
    }

    fun deleteSubscriber(userId: UUID, subscriberId: UUID) {
        val query = "DELETE FROM users_subscribers " +
                "WHERE user_id = \'$userId' AND subscribers_id = \'$subscriberId'"

        logger.warn { query }

        jdbcTemplate.update(query)
    }

    private companion object {
        val ROW_MAPPER_USERS_SUBSCRIBER= RowMapper<SubscriberDto> { rs, _ ->
            SubscriberDto(
                rs.getObject("user_id", UUID::class.java),
                rs.getString("username"),
                rs.getString("first_name"),
                rs.getString("second_name")
            )
        }
    }
}