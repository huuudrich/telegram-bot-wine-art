package com.wineart.bot.api

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller

@Controller
class CommandHandler {

    @Value("\${auth.bot.token}")
    private lateinit var token: String

    @PostConstruct
    fun handler() {
        val bot = bot {
            token = "6926579582:AAFBQTGafqw6CSHzar9BdTXcpwRpO9ANmRc"
            dispatch {
                command("start") {
                    val result = bot.sendMessage(chatId = ChatId.fromId(message.chat.id),text = "Здарова заебал")
                }
            }
        }
        bot.startPolling()
    }
}