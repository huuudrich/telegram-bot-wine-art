package com.wineart.user.commands

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.Update
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import com.wineart.service.user.UserService
import com.wineart.service.user.arguments.CreateOrUpdateArg
import com.wineart.user.UserCommandsName.BUY_CERTIFICATE
import com.wineart.user.UserCommandsName.PRICE_OF_EVENTS
import com.wineart.utils.VoidAction
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.*

@Component
class StartUserCommand(
    private val messageSource: MessageSource,
    private val userService: UserService
                      ) : VoidAction<Bot, Update> {

    override fun execute(bot: Bot, argument: Update) {
        val chatId = argument.message?.chat?.id ?: return

        val keyboardMarkup = KeyboardReplyMarkup(
            keyboard = listOf(
                listOf(
                    KeyboardButton(BUY_CERTIFICATE.s),
                    KeyboardButton(PRICE_OF_EVENTS.s)
                      )
                             ),
            resizeKeyboard = true,
            oneTimeKeyboard = true,
            selective = true
                                                )

        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = messageSource.getMessage("start.command.message", null, Locale.getDefault()),
            parseMode = ParseMode.HTML,
            replyMarkup = keyboardMarkup
                       )

        userService.createOrUpdate(
            chatId, CreateOrUpdateArg(
                telegramUsername = argument.message!!.from?.username,
                                     )
                                  )
    }
}