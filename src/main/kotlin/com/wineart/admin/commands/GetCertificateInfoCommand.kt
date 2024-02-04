package com.wineart.admin.commands

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Update
import com.wineart.model.user.UserState
import com.wineart.service.user.UserService
import com.wineart.utils.VoidAction
import org.springframework.stereotype.Component

@Component
class GetCertificateInfoCommand(private val userService: UserService) : VoidAction<Bot, Update> {
    override fun execute(bot: Bot, argument: Update) {
        val chatId = argument.message?.chat?.id ?: return

        bot.sendMessage(ChatId.fromId(chatId), "Введите номер транзакции сертификата")

        userService.updateState(chatId, UserState.ADMIN_PAYMENT_NUMBER_ENTRY)
    }
}