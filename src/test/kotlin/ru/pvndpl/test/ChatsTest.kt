package ru.pvndpl.test

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.pvndpl.controller.ChatController
import ru.pvndpl.controller.PostController
import ru.pvndpl.entity.Chat
import ru.pvndpl.model.ChatDto
import ru.pvndpl.mother.ChatMother
import ru.pvndpl.repository.ChatRepository
import ru.pvndpl.repository.PostRepository
import ru.pvndpl.service.ChatService
import ru.pvndpl.service.PostService
import ru.pvndpl.service.UserService
import java.util.*


@SpringBootTest
class ChatsTest {

    companion object {
        var userId: UUID = UUID.randomUUID()
    }

    @MockBean
    lateinit var chatRepository: ChatRepository

    @MockBean
    lateinit var userService: UserService

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun beforeEach() {

        val chatService = ChatService(chatRepository, userService)

        mockMvc = MockMvcBuilders.standaloneSetup(ChatController(chatService)).build()
    }

    @Test
    fun getAllChatsByUserId() {

        val chatDto : ChatDto = ChatMother.chat()

        val objectMapper : ObjectMapper = ObjectMapper()

        `when`(chatRepository.getAllUserChats(any())).thenReturn(listOf(chatDto))

        mockMvc
            .get("/chats/${userId}")
            .andDo { print() }
            .andExpect { status {isOk()} }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(listOf(chatDto)))
                }
            }
    }

    private fun <T> any(): T = Mockito.any()
}