package org.wine.art.bot.user.message

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.config.MessagesProperties
import org.wine.art.model.UserCommandName.BUY_CERTIFICATE
import org.wine.art.model.user.Role
import org.wine.art.model.user.UserState
import org.wine.art.service.user.UserService
import org.wine.art.utils.createMessage
import org.wine.art.utils.handlers.BotMessageHandler
import java.util.*

@Component
class BuyCertificate(
    private val userService: UserService,
    private val messagesProperties: MessagesProperties
                    ) :
    BotMessageHandler(BUY_CERTIFICATE.text, "Купить сертификат", EnumSet.of(Role.ADMIN, Role.USER)) {

    override fun handle(absSender: AbsSender, message: Message, chat: Chat) {
        val chatId = chat.id.toString()

        absSender.execute(createMessage(chatId, messagesProperties.payment.command.message))

        userService.updateState(chat.id, UserState.USER_PAYMENT_AMOUNT_CERTIFICATE)
    }
}