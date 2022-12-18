package ru.pvndpl.service

import org.springframework.stereotype.Service
import ru.pvndpl.entity.Profile
import ru.pvndpl.model.ProfileAboutDto
import ru.pvndpl.model.ProfileInterestsDto
import ru.pvndpl.model.ProfilePersonalInfDto
import ru.pvndpl.model.ProfileSocialDto
import ru.pvndpl.repository.ProfileRepository
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoField
import java.util.*

@Service
class ProfileService(
    val profileRepository: ProfileRepository,
    val userService: UserService
) {

    fun getUserProfile(userId: UUID): Profile? {

        return profileRepository.getUserProfile(userId)
    }

    fun getUserAuthProfile(userName: String): Profile? {

        val userId: UUID = userService.findByUsername(userName)!!.id

        return profileRepository.getUserProfile(userId)
    }

    fun createUserProfile(userId: UUID, email: String) {

        val now = LocalDateTime.now()
        val nowInMillis = (now.toEpochSecond(ZoneOffset.UTC) * 1000
                + now[ChronoField.MILLI_OF_SECOND])

        profileRepository.createUserProfile(userId, Date(nowInMillis), email)
    }

    fun editUserProfileAbout(userName: String, profileAboutDto: ProfileAboutDto) {

        val userId: UUID = userService.findByUsername(userName)!!.id

        profileRepository.editAbout(
            userId,
            profileAboutDto.about,
            profileAboutDto.city,
            profileAboutDto.website
        )
    }

    fun editUserProfileInterests(userName: String, profileInterestsDto: ProfileInterestsDto) {

        val userId: UUID = userService.findByUsername(userName)!!.id

        profileRepository.editInterests(
            userId,
            profileInterestsDto.tvShows,
            profileInterestsDto.showmen,
            profileInterestsDto.movies,
            profileInterestsDto.books,
            profileInterestsDto.games
        )
    }

    fun editUserProfilePersonalInf(userName: String, profilePersonalInfDto: ProfilePersonalInfDto) {

        val userId: UUID = userService.findByUsername(userName)!!.id

        profileRepository.editPersonalInf(
            userId,
            profilePersonalInfDto.email,
            profilePersonalInfDto.birthdate,
            profilePersonalInfDto.busyness,
            profilePersonalInfDto.nativeCity
        )
    }

    fun addUserProfileSocialNetwork(userName: String, profileSocialDto: ProfileSocialDto) {

        val userId: UUID = userService.findByUsername(userName)!!.id

        profileRepository.addSocialNetwork(userId, profileSocialDto.socialId, profileSocialDto.url)
    }

    fun removeUserProfileSocialNetwork(userName: String, socialId: Int) {

        val userId: UUID = userService.findByUsername(userName)!!.id

        profileRepository.removeSocialNetwork(userId, socialId)
    }

    fun editUserProfileSocialNetwork(userName: String, profileSocialDto: ProfileSocialDto) {

        val userId: UUID = userService.findByUsername(userName)!!.id

        profileRepository.editSocialNetwork(userId, profileSocialDto.socialId, profileSocialDto.url)
    }

    //admin
    fun createSocialNetwork(title: String) {
        profileRepository.createSocialNetwork(title)
    }
}
