package org.wine.art.bot.admin.state

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.bot.admin.action.BuildCertificateInfo
import org.wine.art.model.certificate.CertificateStatus
import org.wine.art.model.user.Role
import org.wine.art.model.user.UserState.ADMIN_CHANGE_COST_CERTIFICATE_ENTRY
import org.wine.art.service.certificate.CertificateService
import org.wine.art.service.certificate.arguments.UpdateCertificateArg
import org.wine.art.service.user.UserService
import org.wine.art.utils.createMessage
import org.wine.art.utils.handlers.UserStatesHandler
import java.util.*

@Component
class ChangeCostCertificateEntry(
    private val userService: UserService,
    private val buildCertificateInfo: BuildCertificateInfo,
    private val certificateService: CertificateService,
                                ) : UserStatesHandler(
    ADMIN_CHANGE_COST_CERTIFICATE_ENTRY,
    "Изменить сумму сертификата", EnumSet.of(Role.ADMIN)
                                                     ) {

    override fun handle(absSender: AbsSender, message: Message, chat: Chat) {
        val chatId = chat.id

        val newSum = message.text.toIntOrNull() ?: return

        if (newSum < 0 || newSum > 10000) {
            absSender.execute(
                createMessage(chatId.toString(), "Сумма не может быть меньше 0 или больше 10000")
                             )
            return
        }

        val user = userService.getExisting(chatId)
        val paymentId = UUID.fromString(user.callBackQueryInfo)

        if (newSum == 0) {
            certificateService.updateStatusAndCost(
                paymentId,
                UpdateCertificateArg(newSum, CertificateStatus.DEACTIVATED)
                                                  )
        } else {
            certificateService.updateStatusAndCost(
                paymentId,
                UpdateCertificateArg(newSum, CertificateStatus.ACTIVATED)
                                                  )
        }

        val sendMessage = buildCertificateInfo.execute(paymentId.toString(), chatId)

        val editMessageText = EditMessageText.builder()
            .chatId(sendMessage.chatId)
            .messageId(user.messageId?.toInt() ?: return)
            .text(sendMessage.text)
            .parseMode(sendMessage.parseMode)
            .replyMarkup(buildCertificateInfo.getKeyboard())
            .build()

        absSender.execute(editMessageText)
    }
}