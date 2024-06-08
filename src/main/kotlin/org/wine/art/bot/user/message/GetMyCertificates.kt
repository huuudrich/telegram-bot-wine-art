package org.wine.art.bot.user.message

import InMemoryStorage.storeCertificates
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.UserCommandName
import org.wine.art.model.user.Role
import org.wine.art.service.certificate.CertificateService
import org.wine.art.service.user.UserService
import org.wine.art.utils.CertificateUtil.sendCertificates
import org.wine.art.utils.handlers.BotMessageHandler
import java.util.*

@Component
class GetMyCertificates(
    private val certificateService: CertificateService,
    private val userService: UserService
                       ) :
    BotMessageHandler(
        UserCommandName.GET_MY_CERTIFICATES.text,
        "Посмотреть свои сертификаты",
        EnumSet.of(Role.ADMIN, Role.USER)
                     ) {

    override fun handle(absSender: AbsSender, message: Message, chat: Chat) {
        val chatId = chat.id

        val sendMessage =
            sendCertificates(
                absSender,
                chatId.toString(),
                storeCertificates(chatId, certificateService.getAllByChatId(chatId), 5)
                            )

        userService.updateMessageId(chatId, sendMessage.messageId.toString())
    }
}