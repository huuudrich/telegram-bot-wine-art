package org.wine.art.bot.admin.message

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.AdminCommandName
import org.wine.art.model.user.Role
import org.wine.art.model.user.UserState
import org.wine.art.service.user.UserService
import org.wine.art.utils.createMessage
import org.wine.art.utils.handlers.BotMessageHandler
import java.util.*

@Component
class GetCertificateInfo(private val userService: UserService) :
    BotMessageHandler(AdminCommandName.GET_CERTIFICATE_INFO.text, "Ввести инфу о сертифкате", EnumSet.of(Role.ADMIN)) {

    override fun handle(absSender: AbsSender, message: Message, chat: Chat) {
        val chatId = chat.id

        absSender.execute(createMessage(chatId.toString(), "Введите номер транзакции сертификата"))

        userService.updateState(chatId, UserState.ADMIN_PAYMENT_NUMBER_ENTRY)
    }
}