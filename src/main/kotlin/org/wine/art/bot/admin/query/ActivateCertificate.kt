package org.wine.art.bot.admin.query

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.bot.admin.action.BuildCertificateInfo
import org.wine.art.model.AdminCommandName
import org.wine.art.model.user.Role
import org.wine.art.service.certificate.CertificateService
import org.wine.art.service.user.UserService
import org.wine.art.utils.handlers.CallbackDataHandler
import java.util.*

@Component
class ActivateCertificate(
    private val userService: UserService,
    private val certificateService: CertificateService,
    private val buildCertificateInfo: BuildCertificateInfo
                         ) :
    CallbackDataHandler(AdminCommandName.ACTIVATE_CERTIFICATE.text, "Активировать сертификат", EnumSet.of(Role.ADMIN)) {

    override fun handle(absSender: AbsSender, user: User, update: Update) {
        val chatId = user.id

        val userDb = userService.getExisting(chatId)
        val paymentId = UUID.fromString(userDb.callBackQueryInfo)

        certificateService.activate(paymentId)

        val sendMessage = buildCertificateInfo.execute(paymentId.toString(), chatId)

        val editMessageText = EditMessageText.builder()
            .chatId(sendMessage.chatId)
            .messageId(userDb.messageId?.toInt() ?: return)
            .text(sendMessage.text)
            .parseMode(sendMessage.parseMode)
            .replyMarkup(buildCertificateInfo.getKeyboard())
            .build()

        absSender.execute(editMessageText)
    }
}