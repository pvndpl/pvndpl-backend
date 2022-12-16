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

    @GetMapping("/subscribers")
    fun getAuthUserSubscribers(auth: Authentication): ResponseEntity<List<SubscriberDto>> {

        return ResponseEntity.ok(subscriberService.getAuthUserSubscribers(auth.name))
    }

    @PostMapping("/subscribers")
    fun setNewSubscriber(auth: Authentication, @RequestParam subscriberId: UUID): ResponseEntity<Void> {

        subscriberService.setNewSubscriber(auth.name, subscriberId)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @DeleteMapping("/subscribers")
    fun deleteSubscriber(auth: Authentication, @RequestParam subscriberId: UUID): ResponseEntity<Void> {

        subscriberService.deleteSubscriber(auth.name, subscriberId)

        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }
}