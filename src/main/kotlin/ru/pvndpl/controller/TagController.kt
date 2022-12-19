package ru.pvndpl.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.pvndpl.model.TagDto
import ru.pvndpl.model.TagSaveDto
import ru.pvndpl.model.UserDto
import ru.pvndpl.service.TagService
import java.util.*

@RestController
class TagController(
    val tagService: TagService
) {

    @GetMapping("/users/{userId}/tags")
    fun fetchUserTags(@PathVariable userId: UUID): ResponseEntity<List<TagDto>> {

        return ResponseEntity.ok(tagService.fetchTags(userId))
    }

    @GetMapping("/profile/tags")
    fun fetchAuthUserTags(auth: Authentication): ResponseEntity<List<TagDto>> {

        return ResponseEntity.ok(tagService.fetchAuthUserTags(auth.name))
    }

    @PostMapping("/profile/tags")
    fun createUserTag(auth: Authentication, @RequestParam tag: String) {

        tagService.createUserTag(auth.name, tag)
    }

    @GetMapping("/tags")
    fun fetchTags(): ResponseEntity<List<TagDto>> {

        return ResponseEntity.ok(tagService.fetchTags())
    }

    @GetMapping("/tags/{tagName}")
    fun getAllUsersByTagName(@PathVariable tagName: String): ResponseEntity<List<UserDto>> {

        return ResponseEntity.ok(tagService.getAllUsersByTagName(tagName))
    }



    @PostMapping("/tags")
    fun createTag(@RequestBody tagSaveDto: TagSaveDto): ResponseEntity<TagDto> {

        return ResponseEntity.ok(tagService.createTag(tagSaveDto))
    }
}