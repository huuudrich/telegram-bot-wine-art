package com.wineart.bot.commands

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.Update
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import com.wineart.bot.model.MessageTexts
import com.wineart.bot.utils.VoidMessage
import org.springframework.stereotype.Component

@Component
class StartCommand : VoidMessage {
    override fun execute(bot: Bot, update: Update) {
        val chatId = update.message?.chat?.id

        chatId?.let { ChatId.fromId(it) }?.let { bot.sendMessage(it, "start.command.message") }

        val keyboardMarkup = KeyboardReplyMarkup(
            keyboard = listOf(
                listOf(
                    KeyboardButton(text = MessageTexts.SIGN_LESSON.s),
                    KeyboardButton(text = MessageTexts.TIMETABLE.s)
                      ),
                listOf(
                    KeyboardButton(text = MessageTexts.BUY_CERTIFICATE.s),
                    KeyboardButton(text = MessageTexts.BOOK_DATE.s)
                      )
                             ),
            resizeKeyboard = true,
            oneTimeKeyboard = true,
            selective = true
                                                )
        chatId?.let { ChatId.fromId(it) }?.let {
            bot.sendMessage(
                chatId = it,
                text = "Выберите действие:",
                replyMarkup = keyboardMarkup
                           )
        }
    }
}