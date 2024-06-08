package org.wine.art.bot.admin.message

import InMemoryStorage.storeCertificates
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.AdminCommandName
import org.wine.art.model.user.Role
import org.wine.art.service.certificate.CertificateService
import org.wine.art.service.user.UserService
import org.wine.art.utils.CertificateUtil.sendCertificates
import org.wine.art.utils.handlers.BotMessageHandler
import java.util.*

@Component
class GetAllCertificates(
    private val userService: UserService,
    private val certificateService: CertificateService
                        ) : BotMessageHandler(
    AdminCommandName.GET_ALL_CERTIFICATES.text,
    "Ввести инфу о всех сертификатах",
    EnumSet.of(Role.ADMIN)
                                             ) {

    override fun handle(absSender: AbsSender, message: Message, chat: Chat) {
        val chatId = chat.id

        val sendMessage =
            sendCertificates(absSender, chatId.toString(), storeCertificates(chatId, certificateService.getAll(), 5))

        userService.updateMessageId(chatId, sendMessage.messageId.toString())
    }
}