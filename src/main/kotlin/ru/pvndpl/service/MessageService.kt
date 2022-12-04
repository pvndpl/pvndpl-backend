package ru.pvndpl.service

import org.springframework.stereotype.Service
import ru.pvndpl.entity.Message
import ru.pvndpl.model.MessageCreateDto
import ru.pvndpl.repository.MessageRepository
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoField
import java.util.*


@Service
class MessageService(
    val messageRepository: MessageRepository,
    val userService: UserService
) {

    fun getAllMessagesByChatId(chatId: UUID): List<Message> {
        return messageRepository.getAllMessagesByChatId(chatId)
    }

    fun sendMessage(messageCreateDto: MessageCreateDto, chatId: UUID, userName: String): Message {

        val userId: UUID = userService.findByUsername(userName)!!.id

        val now = LocalDateTime.now()
        val nowInMillis = (now.toEpochSecond(ZoneOffset.UTC) * 1000
                + now[ChronoField.MILLI_OF_SECOND])

        val messageId: UUID = messageRepository.sendMessage(
            userId,
            Timestamp(nowInMillis),
            messageCreateDto.text, chatId)

        return messageRepository.getMessageById(messageId) ?: throw RuntimeException()
    }
}