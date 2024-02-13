package com.wineart.admin.callback

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.CallbackQuery
import com.github.kotlintelegrambot.entities.ChatId
import com.wineart.model.user.UserState
import com.wineart.service.user.UserService
import com.wineart.utils.VoidAction
import org.springframework.stereotype.Component

@Component
class ChangeCostCertificateCallback(
    private val userService: UserService
                                   ) : VoidAction<Bot, CallbackQuery> {
    override fun execute(bot: Bot, argument: CallbackQuery) {
        val chatId = argument.message?.chat?.id ?: return

        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            "Введите новую сумму (если сумма будет 0 - сертификат заблокируется)"
                       )

        userService.updateState(chatId, UserState.ADMIN_CHANGE_COST_CERTIFICATE_ENTRY)
    }
}