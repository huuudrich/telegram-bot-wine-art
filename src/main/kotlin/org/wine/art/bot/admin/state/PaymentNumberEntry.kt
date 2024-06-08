package org.wine.art.bot.admin.state

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.bot.admin.action.BuildCertificateInfo
import org.wine.art.model.user.Role
import org.wine.art.model.user.UserState.ADMIN_PAYMENT_NUMBER_ENTRY
import org.wine.art.service.user.UserService
import org.wine.art.utils.handlers.UserStatesHandler
import java.util.*

@Component
class PaymentNumberEntry(
    private val userService: UserService,
    private val buildCertificateInfo: BuildCertificateInfo
                        ) : UserStatesHandler(
    ADMIN_PAYMENT_NUMBER_ENTRY,
    "Отправить инфу о сертификате", EnumSet.of(Role.ADMIN)
                                             ) {

    override fun handle(absSender: AbsSender, message: Message, chat: Chat) {
        val chatId = chat.id

        val paymentId = message.text ?: return

        val sentMessage = absSender.execute(buildCertificateInfo.execute(paymentId, chatId))

        userService.updateMessageId(chatId, sentMessage.messageId.toString())
        userService.resetState(chatId)
        userService.updateCallBackQueryInfo(chatId, paymentId)
    }
}