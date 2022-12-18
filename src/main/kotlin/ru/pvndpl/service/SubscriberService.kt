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

    fun createSubscriptions(userName: String, subscriptionId: UUID) {

        val userId: UUID = userService.findByUsername(userName)!!.id

        if (userId == subscriptionId) {
            throw Exception("Нельзя подписаться на самого себя")
        }

        if (subscriberRepository.hasSubscription(userId, subscriptionId) == true) {
            throw Exception("Вы уже подписаны на этого индивида")
        }

        subscriberRepository.setNewSubscriber(userId, subscriptionId)
    }

    fun deleteSubscriber(userName: String, subscriberId: UUID) {

        val userId: UUID = userService.findByUsername(userName)!!.id

        if (userId == subscriberId) {
            throw Exception("Нельзя удалить подписку на самого сего (её не существует)")
        }

        if (subscriberRepository.hasSubscription(userId, subscriberId) == false) {
            throw Exception("Вы не подписаны на этого индивида")
        }

        subscriberRepository.deleteSubscriber(userId, subscriberId)
    }

}