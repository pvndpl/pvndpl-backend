package ru.pvndpl.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import ru.pvndpl.entity.Message
import ru.pvndpl.model.MessageCreateDto
import ru.pvndpl.service.MessageService
import java.util.*

@Controller
class MessageController(
    val messageService: MessageService
) {

    @GetMapping("/chats/{id}")
    fun getAllMessagesByChatId(@PathVariable id: UUID): ResponseEntity<List<Message>> {

        val chats: List<Message> = messageService.getAllMessagesByChatId(id)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(chats)
    }

    @PostMapping("/chats/{id}")
    fun sendMessage(
        auth: Authentication,
        @PathVariable id: UUID,
        @RequestBody messageCreateDto: MessageCreateDto
    ):
            ResponseEntity<Message> {

        val message: Message = messageService.sendMessage(messageCreateDto, id, auth.name)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(message)
    }

}