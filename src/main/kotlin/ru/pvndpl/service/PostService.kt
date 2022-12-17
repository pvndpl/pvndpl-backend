package ru.pvndpl.service

import org.springframework.stereotype.Service
import ru.pvndpl.entity.Post
import ru.pvndpl.model.PostCreateDto
import ru.pvndpl.repository.PostRepository
import java.util.UUID

@Service
class PostService(
    val postRepository : PostRepository,
    val userService: UserService
) {
    fun createPost(userName: String, postDto : PostCreateDto) : UUID {

        val creatorId: UUID = userService.findByUsername(userName)!!.id

        return postRepository.createPost(creatorId, postDto.category, postDto.content)
    }

    fun getAllPosts(userName: String) : List<Post>{

        val userId: UUID = userService.findByUsername(userName)!!.id

        return postRepository.getAllPosts(userId)
    }

    fun getUserPosts(id : UUID) : List<Post>{

        return postRepository.getUserPosts(id)
    }
}