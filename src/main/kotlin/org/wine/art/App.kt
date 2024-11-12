package org.wine.art

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.wine.art.config.MessagesProperties

@EnableConfigurationProperties(MessagesProperties::class)
@SpringBootApplication
class App

fun main(args: Array<String>) {
	runApplication<App>(*args)
}
