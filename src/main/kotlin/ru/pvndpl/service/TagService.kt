package ru.pvndpl.service

import org.springframework.stereotype.Service
import ru.pvndpl.model.TagDto
import ru.pvndpl.model.TagSaveDto
import ru.pvndpl.repository.TagRepository
import java.util.*

@Service
class TagService(
    val tagRepository: TagRepository
) {

    fun createTag(tagSaveDto: TagSaveDto): TagDto {

        return tagRepository.createTag(tagSaveDto.name, tagSaveDto.sysname)
    }

    fun fetchTags(userId: UUID): List<TagDto> {

        return tagRepository.fetchTags(userId);
    }

    fun createUserTag(userId: UUID, tagSysname: String) {

        val tagId: UUID? = tagRepository.findTagIdBySysname(tagSysname);

        if (tagId != null) {
            tagRepository.createUserTag(userId, tagId)
        } else {
            throw Exception("Incorrect sysname")
        }
    }

    fun fetchTags(): List<TagDto> {

        return tagRepository.fetchTags()
    }
}