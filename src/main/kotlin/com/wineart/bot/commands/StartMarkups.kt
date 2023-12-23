package com.wineart.bot.commands

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.Update
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import com.wineart.bot.model.MessageTexts.StartMarkupsText.*
import com.wineart.bot.utils.VoidMessage
import org.springframework.stereotype.Component

@Component
class StartMarkups : VoidMessage {
    override fun execute(bot: Bot, update: Update) {
        val chatId = update.message?.chat?.id

        val keyboardMarkup = KeyboardReplyMarkup(
                keyboard = listOf(
                        listOf(KeyboardButton(text = SIGN_LESSON.s), KeyboardButton(text = TIMETABLE.s)),
                        listOf(KeyboardButton(text = BUY_CERTIFICATE.s), KeyboardButton(text = BOOK_DATE.s))
                ),
                resizeKeyboard = true,
                oneTimeKeyboard = true,
                selective = true
        )

        chatId?.let { ChatId.fromId(it) }?.let {
            bot.sendMessage(
                    chatId = it,
                    text = "Choose an option",
                    replyMarkup = keyboardMarkup
            )
        }
    }
}