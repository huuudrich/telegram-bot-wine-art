package com.wineart.commands.user

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Update
import com.wineart.model.user.UserState
import com.wineart.service.user.UserService
import com.wineart.utils.VoidAction
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.*

@Component
class BuyCertificateCommand(
    private val messageSource: MessageSource,
    private val userService: UserService
                           ) : VoidAction {
    override fun execute(bot: Bot, update: Update) {
        val chatId = update.message?.chat?.id ?: return

        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = messageSource.getMessage("payment.command.message", null, Locale.getDefault()),
                       )

        userService.updateState(chatId, UserState.PAYMENT_AMOUNT_CERTIFICATE)
    }
}