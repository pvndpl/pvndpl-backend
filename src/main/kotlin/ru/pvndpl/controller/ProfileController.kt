package ru.pvndpl.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.pvndpl.entity.Profile
import ru.pvndpl.model.ProfileAboutDto
import ru.pvndpl.model.ProfileInterestsDto
import ru.pvndpl.model.ProfilePersonalInfDto
import ru.pvndpl.model.ProfileSocialDto
import ru.pvndpl.service.ProfileService
import java.util.*

@Controller
class ProfileController(
    val profileService: ProfileService
) {

    @GetMapping("/users/{userId}")
    fun getUserProfile(@PathVariable userId: UUID): ResponseEntity<Profile?> {

        return ResponseEntity.ok(profileService.getUserProfile(userId))
    }

    @GetMapping("/profile")
    fun getUserAuthProfile(auth: Authentication): ResponseEntity<Profile?> {

        return ResponseEntity.ok(profileService.getUserAuthProfile(auth.name))
    }

    @PatchMapping("/profile/about")
    fun editUserProfileAbout(auth: Authentication, dto: ProfileAboutDto): ResponseEntity<Void> {

        profileService.editUserProfileAbout(auth.name, dto)

        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }

    @PatchMapping("/profile/interests")
    fun editUserProfileInterests(auth: Authentication, dto: ProfileInterestsDto): ResponseEntity<Void> {

        profileService.editUserProfileInterests(auth.name, dto)

        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }

    @PatchMapping("/profile/personalInf")
    fun editUserProfilePersonalInf(auth: Authentication, dto: ProfilePersonalInfDto): ResponseEntity<Void> {

        profileService.editUserProfilePersonalInf(auth.name, dto)

        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }

    @PostMapping("/profile/socials")
    fun addUserProfileSocialNetwork(auth: Authentication, profileSocialDto: ProfileSocialDto): ResponseEntity<Void> {

        profileService.addUserProfileSocialNetwork(auth.name, profileSocialDto)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @DeleteMapping("/profile/socials")
    fun removeUserProfileSocialNetwork(auth: Authentication, @RequestParam socialId: Int): ResponseEntity<Void> {

        profileService.removeUserProfileSocialNetwork(auth.name, socialId)

        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }

    @PatchMapping("/profile/socials")
    fun editUserProfileSocialNetwork(auth: Authentication, profileSocialDto: ProfileSocialDto): ResponseEntity<Void> {

        profileService.editUserProfileSocialNetwork(auth.name, profileSocialDto)

        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }

    //admin
    @PostMapping("socials")
    fun createSocialNetwork(@RequestParam title: String): ResponseEntity<Void> {

        profileService.createSocialNetwork(title)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }
}
