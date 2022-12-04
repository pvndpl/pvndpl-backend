package ru.pvndpl.service

import org.springframework.stereotype.Service
import ru.pvndpl.model.ChatCreateDto
import ru.pvndpl.model.ChatDto
import ru.pvndpl.repository.ChatRepository
import java.util.*

@Service
class ChatService(
    val chatRepository: ChatRepository,
    val userService: UserService
) {

    fun createChat(chatCreateDto: ChatCreateDto, userName: String): UUID {

        val userId: UUID = userService.findByUsername(userName)!!.id

        return chatRepository.createChat(
            chatCreateDto.type,
            chatCreateDto.title,
            userId
        )
    }

    fun getAllUserChats(userName: String): List<ChatDto> {

        return chatRepository.getAllUserChats(userService.findByUsername(userName)!!.id)
    }
}