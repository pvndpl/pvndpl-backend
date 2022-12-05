package ru.pvndpl.test

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.pvndpl.controller.SubscriberController
import ru.pvndpl.repository.SubscriberRepository
import ru.pvndpl.service.SubscriberService
import ru.pvndpl.service.UserService
import java.util.*
import ru.pvndpl.model.SubscriberDto
import ru.pvndpl.mother.SubscriberFather

@SpringBootTest
class SubscriberTest {
    companion object {
        var userId: UUID = UUID.randomUUID()
    }

    @MockBean
    lateinit var subscriberRepository: SubscriberRepository

    @MockBean
    lateinit var userService: UserService

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun beforeEach() {

        val subscriberService = SubscriberService(subscriberRepository, userService)

        mockMvc = MockMvcBuilders.standaloneSetup(SubscriberController(subscriberService)).build()
    }

    @Test
    fun testGetUserAuthProfile() {

        val subscriber: SubscriberDto = SubscriberFather.subscriberDto()

        val objectMapper = ObjectMapper()

        Mockito.`when`(subscriberRepository.getUserSubscribers(any())).thenReturn(listOf(subscriber))

        mockMvc
            .get("/subscribers/${userId}")
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(listOf(subscriber)))
                }
            }

        Mockito.verify(subscriberRepository, Mockito.times(1)).getUserSubscribers(any())
    }

    private fun <T> any(): T = Mockito.any()
}