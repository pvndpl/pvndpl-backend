package ru.pvndpl.service

import org.springframework.stereotype.Service
import ru.pvndpl.entity.Post
import ru.pvndpl.model.PostCreateDto
import ru.pvndpl.repository.PostRepository
import java.util.UUID

@Service
class PostService(val postRepository : PostRepository) {
    fun createPost(postDto : PostCreateDto) : UUID{

        return postRepository.createPost(postDto.creatorId, postDto.category, postDto.content)
    }

    fun getAllPosts() : List<Post>{

        return postRepository.getAllPosts()
    }

    fun getUserPosts(id : UUID) : List<Post>{

        return postRepository.getUserPosts(id)
    }
}