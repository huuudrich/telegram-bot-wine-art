package com.wineart.config

import com.github.kotlintelegrambot.bot
import com.wineart.launcher.CommandsHandler
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class BotConfig(val commandsHandler: CommandsHandler) {

    @Value("\${auth.bot.token}")
    private lateinit var tokenInit: String

    @PostConstruct
    fun init() {
        val bot = bot {
            token = tokenInit
            commandsHandler.execute(this)
        }
        bot.startPolling()
    }
}