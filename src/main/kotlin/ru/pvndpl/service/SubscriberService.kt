package ru.pvndpl.service

import org.springframework.stereotype.Service
import ru.pvndpl.model.SubscriberDto
import ru.pvndpl.repository.SubscriberRepository
import java.util.*

@Service
class SubscriberService(
    val subscriberRepository: SubscriberRepository,
    val userService: UserService
) {
    fun getUserSubscribers(userId: UUID): List<SubscriberDto> {

        return subscriberRepository.getUserSubscribers(userId)
    }

    fun getUserSubscriptions(userId: UUID): List<SubscriberDto> {

        return subscriberRepository.getUserSubscriptions(userId)
    }

    fun getAuthUserSubscriptions(userName: String): List<SubscriberDto> {

        val userId: UUID = userService.findByUsername(userName)!!.id

        return subscriberRepository.getUserSubscribers(userId)
    }

    fun getAuthUserSubscribers(userName: String): List<SubscriberDto> {

        val userId: UUID = userService.findByUsername(userName)!!.id

        return subscriberRepository.getUserSubscriptions(userId)
    }

    fun setNewSubscriber(userName: String, subscriberId: UUID) {

        val userId: UUID = userService.findByUsername(userName)!!.id

        if (userId == subscriberId)
            throw IllegalArgumentException("user id and subscription id are the same")

        subscriberRepository.setNewSubscriber(userId, subscriberId)
    }

    fun deleteSubscriber(userName: String, subscriberId: UUID) {

        val userId: UUID = userService.findByUsername(userName)!!.id

        subscriberRepository.deleteSubscriber(userId, subscriberId)
    }

}