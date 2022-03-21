package com.example.asyncmethod

import mu.*
import org.springframework.boot.*
import org.springframework.stereotype.*
import java.util.concurrent.*

@Component
class AppRunner(val gitHubLookupService: GitHubLookupService): CommandLineRunner {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun run(vararg args: String?) {
        // Start the clock
        val start = System.currentTimeMillis()

        // Kick of multiple asynchronous lookups
        val page1 = gitHubLookupService.findUser("PivotalSoftware")
        val page2 = gitHubLookupService.findUser("CloudFoundry")
        val page3 = gitHubLookupService.findUser("Spring-Projects")

        // Wait  until they are all done
        CompletableFuture.allOf(page1,page2,page3).join()

        // Print results, including elapsed time
        logger.info { "Elapsed time: ${System.currentTimeMillis() - start}" }
        logger.info { "--> ${page1.get()}" }
        logger.info { "--> ${page2.get()}" }
        logger.info { "--> ${page3.get()}" }


    }

}