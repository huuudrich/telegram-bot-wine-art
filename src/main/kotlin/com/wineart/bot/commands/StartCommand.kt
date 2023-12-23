package com.wineart.bot.commands

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Update
import com.wineart.bot.utils.VoidMessage
import lombok.NonNull
import org.springframework.stereotype.Component

@Component
class StartCommand : VoidMessage {
    override fun execute(@NonNull bot: Bot, @NonNull update: Update) {
        val chatId = update.message?.chat?.id

        chatId?.let { ChatId.fromId(it) }?.let { bot.sendMessage(it,"start.command.message") }
    }
}