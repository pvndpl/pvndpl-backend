package ru.pvndpl.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.pvndpl.model.TagDto
import ru.pvndpl.model.TagSaveDto
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

    @PostMapping("/users/{userId}/tags")
    fun createUserTag(@PathVariable userId: UUID, @RequestParam tag: String) {

        tagService.createUserTag(userId, tag)
    }

    @GetMapping("/tags")
    fun fetchTags(): ResponseEntity<List<TagDto>> {

        return ResponseEntity.ok(tagService.fetchTags())
    }

    @PostMapping("/tags")
    fun createTag(@RequestBody tagSaveDto: TagSaveDto): ResponseEntity<TagDto> {

        return ResponseEntity.ok(tagService.createTag(tagSaveDto))
    }
}