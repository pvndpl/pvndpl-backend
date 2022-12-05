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
import ru.pvndpl.controller.ProfileController
import ru.pvndpl.mother.ProfileParent1
import ru.pvndpl.repository.ProfileRepository
import ru.pvndpl.service.ProfileService
import ru.pvndpl.service.UserService
import ru.pvndpl.entity.Profile
import java.util.*

@SpringBootTest
class ProfileTest {

    companion object {
        var userId: UUID = UUID.randomUUID()
    }

    @MockBean
    lateinit var profileRepository: ProfileRepository

    @MockBean
    lateinit var userService: UserService

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun beforeEach() {

        val profileService = ProfileService(profileRepository, userService)

        mockMvc = MockMvcBuilders.standaloneSetup(ProfileController(profileService)).build()
    }

    @Test
    fun testGetUserAuthProfile() {

        val profile: Profile = ProfileParent1.message()

        val objectMapper = ObjectMapper()

        Mockito.`when`(profileRepository.getUserProfile(any())).thenReturn(profile)

        mockMvc
            .get("/users/${userId}")
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(profile))
                }
            }

        Mockito.verify(profileRepository, Mockito.times(1)).getUserProfile(any())
    }

    private fun <T> any(): T = Mockito.any()
}
