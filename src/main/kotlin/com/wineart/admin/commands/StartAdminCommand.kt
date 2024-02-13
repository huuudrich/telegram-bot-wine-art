package com.wineart.admin.commands

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.Update
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import com.wineart.admin.AdminCommandsName
import com.wineart.utils.VoidAction
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.*

@Component
class StartAdminCommand(
    private val messageSource: MessageSource
                       ) : VoidAction<Bot, Update> {
    override fun execute(bot: Bot, argument: Update) {
        val chatId = argument.message?.chat?.id ?: return

        val keyboardMarkup = KeyboardReplyMarkup(
            keyboard = listOf(
                listOf(
                    KeyboardButton(AdminCommandsName.GET_CERTIFICATE_INFO.s)
                      )
                             ),
            resizeKeyboard = true,
            oneTimeKeyboard = false,
            selective = true
                                                )

        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = messageSource.getMessage("start.admin.command.message", null, Locale.getDefault()),
            parseMode = ParseMode.HTML,
            replyMarkup = keyboardMarkup
                       )
    }
}