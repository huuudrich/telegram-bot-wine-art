package com.wineart.bot.commands

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Update
import com.wineart.bot.utils.VoidMessage
import org.springframework.stereotype.Component

@Component
class SignLessonCommand : VoidMessage {
    override fun execute(bot: Bot, update: Update) {
        val chatId = update.message?.chat?.id
    }
}