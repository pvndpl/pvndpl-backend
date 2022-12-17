package ru.pvndpl.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.pvndpl.entity.Post
import ru.pvndpl.model.PostCreateDto
import ru.pvndpl.service.PostService
import java.util.*

@RestController
class PostController constructor (val postService : PostService) {

    @PostMapping("/posts")
    fun createPost(auth: Authentication, @RequestBody postDto : PostCreateDto) : ResponseEntity<Void> {

        postService.createPost(auth.name, postDto)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @GetMapping("/posts")
    fun getAllPosts(auth: Authentication) : ResponseEntity<List<Post>> {

        val posts : List<Post> = postService.getAllPosts(auth.name)

        return ResponseEntity.ok(posts)
    }

    @GetMapping("/posts/{postId}")
    fun getUserPosts(@PathVariable postId : UUID) : ResponseEntity<List<Post>> {

        val posts : List<Post> = postService.getUserPosts(postId)

        return ResponseEntity.ok(posts)
    }
}