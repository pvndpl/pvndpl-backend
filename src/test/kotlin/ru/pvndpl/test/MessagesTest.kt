package ru.pvndpl.test

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
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
import ru.pvndpl.controller.MessageController
import ru.pvndpl.entity.Message
import ru.pvndpl.mother.MessageMother
import ru.pvndpl.repository.MessageRepository
import ru.pvndpl.service.MessageService
import ru.pvndpl.service.UserService
import java.util.*

@SpringBootTest
class MessagesTest {

    companion object {
        var CHAT_ID: UUID = UUID.randomUUID()
    }

    @MockBean
    lateinit var messageRepository: MessageRepository

    @MockBean
    lateinit var userService: UserService

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun beforeEach() {

        val messageService = MessageService(messageRepository, userService)

        mockMvc = MockMvcBuilders.standaloneSetup(MessageController(messageService)).build()
    }

    @Test
    fun testGetAllMessagesByChatId() {

        val message: Message = MessageMother.message()

        val objectMapper: ObjectMapper = ObjectMapper()

        `when`(messageRepository.getAllMessagesByChatId(any())).thenReturn(listOf(message))

        mockMvc
            .get("/chats/$CHAT_ID")
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(listOf(message)))
                }
            }
    }

    private fun <T> any(): T = Mockito.any()
}