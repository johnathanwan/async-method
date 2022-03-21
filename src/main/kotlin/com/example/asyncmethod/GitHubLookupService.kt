package com.example.asyncmethod

import mu.*
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.concurrent.*

@Service
class GitHubLookupService(restTemplateBuilder: RestTemplateBuilder) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }
    val restTemplate: RestTemplate = restTemplateBuilder.build()

    @Async
    fun findUser(user: String): CompletableFuture<User> {
        logger.info { "Looking up $user" }
        val url = "https://api.github.com/users/$user"
        val results = restTemplate.getForObject(url, User::class.java)

        Thread.sleep(1000L)
        return CompletableFuture.completedFuture(results)
    }
}