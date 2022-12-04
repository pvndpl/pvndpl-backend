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
import ru.pvndpl.controller.PostController
import ru.pvndpl.entity.Post
import ru.pvndpl.mother.PostMother
import ru.pvndpl.repository.PostRepository
import ru.pvndpl.service.PostService
import java.util.*

@SpringBootTest
class PostTest {

    companion object {
        var userId: UUID = UUID.randomUUID()
    }

    @MockBean
    lateinit var postRepository: PostRepository

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun beforeEach() {

        val postService = PostService(postRepository)

        mockMvc = MockMvcBuilders.standaloneSetup(PostController(postService)).build()
    }

    @Test
    fun getAllPostsByUserId(){

        val post : Post = PostMother.post()

        val objectMapper : ObjectMapper = ObjectMapper()

        `when`(postRepository.getUserPosts(any())).thenReturn(listOf(post))

        mockMvc
            .get("/posts/$userId")
            .andDo { print() }
            .andExpect { status {isOk()} }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(listOf(post)))
                }
            }
    }

    @Test
    fun getAllPosts(){
        val post : Post = PostMother.post()

        val objectMapper : ObjectMapper = ObjectMapper()

        `when`(postRepository.getAllPosts()).thenReturn(listOf(post))

        mockMvc
            .get("/posts")
            .andDo { print() }
            .andExpect { status {isOk()} }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(listOf(post)))
                }
            }
    }

    private fun <T> any(): T = Mockito.any()
}