package ru.pvndpl.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import ru.pvndpl.model.ChatCreateDto
import ru.pvndpl.model.ChatDto
import ru.pvndpl.service.ChatService

@Controller
class ChatController(
    val chatService: ChatService
) {

    @GetMapping("/chats")
    fun getAllUserChats(auth: Authentication): ResponseEntity<List<ChatDto>> {

        val chats: List<ChatDto> = chatService.getAllUserChats(auth.name)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(chats)
    }

    @PostMapping("/chats")
    fun createChat(auth: Authentication, @RequestBody chatCreateDto: ChatCreateDto): ResponseEntity<Void> {

        chatService.createChat(chatCreateDto, auth.name)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

}