package ru.pvndpl

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PvndplBackendApplication

fun main(args: Array<String>) {
    runApplication<PvndplBackendApplication>(*args)
}
