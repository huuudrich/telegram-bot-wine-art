package com.wineart.bot.config

import com.github.kotlintelegrambot.bot
import com.wineart.bot.launcher.CommandLauncher
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class BotConfig(val launcher: CommandLauncher) {

    @Value("\${auth.bot.token}")
    private lateinit var tokenInit: String

    @PostConstruct
    fun init() {
        val bot = bot {
            token = tokenInit
            launcher.execute(this)
        }
        bot.startPolling()
    }
}