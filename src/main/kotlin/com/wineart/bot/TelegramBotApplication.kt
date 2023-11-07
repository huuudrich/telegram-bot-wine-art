package com.wineart.bot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TelegramBotWineArtApplication

fun main(args: Array<String>) {
	runApplication<TelegramBotWineArtApplication>(*args)
}
