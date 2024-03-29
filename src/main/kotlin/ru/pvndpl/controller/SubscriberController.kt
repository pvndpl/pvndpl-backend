package ru.pvndpl.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.pvndpl.model.SubscriberDto
import ru.pvndpl.service.SubscriberService
import java.util.*

@RestController
class SubscriberController(
    val subscriberService: SubscriberService
) {
    @GetMapping("/subscribers/{userId}")
    fun getUserSubscribers(@PathVariable userId: UUID): ResponseEntity<List<SubscriberDto>> {

        return ResponseEntity.ok(subscriberService.getUserSubscribers(userId))
    }

    @GetMapping("/subscriptions/{userId}")
    fun getUserSubscriptions(@PathVariable userId: UUID): ResponseEntity<List<SubscriberDto>> {

        return ResponseEntity.ok(subscriberService.getUserSubscriptions(userId))
    }

    @GetMapping("/subscribers")
    fun getAuthUserSubscribers(auth: Authentication): ResponseEntity<List<SubscriberDto>> {

        return ResponseEntity.ok(subscriberService.getAuthUserSubscribers(auth.name))
    }

    @GetMapping("/subscriptions")
    fun getAuthUserSubscriptions(auth: Authentication): ResponseEntity<List<SubscriberDto>> {

        return ResponseEntity.ok(subscriberService.getAuthUserSubscriptions(auth.name))
    }

    /**
     * subscriberId - тот на кого подписываются блять
     */
    @PostMapping("/subscribers/{subscriberId}")
    fun setNewSubscriber(@PathVariable subscriberId: UUID, auth: Authentication): ResponseEntity<Void> {

        subscriberService.createSubscriptions(auth.name, subscriberId)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @GetMapping("/subscriptions-check/{userId}")
    fun checkUserSubscription(@PathVariable userId: UUID, auth: Authentication): ResponseEntity<Boolean> {

        return ResponseEntity.ok(subscriberService.checkUserSubscription(auth.name, userId))
    }

    @DeleteMapping("/subscribers")
    fun deleteSubscriber(auth: Authentication, @RequestParam subscriberId: UUID): ResponseEntity<Void> {

        subscriberService.deleteSubscriber(auth.name, subscriberId)

        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }
}