package org.wine.art.bot.user.query

import InMemoryStorage.getCertificates
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.AdminCommandName
import org.wine.art.model.certificate.Certificate
import org.wine.art.model.user.Role
import org.wine.art.service.user.UserService
import org.wine.art.utils.CertificateUtil.PAGE_SIZE
import org.wine.art.utils.CertificateUtil.buildCertificatesListMessage
import org.wine.art.utils.CertificateUtil.buildNavigationKeyboard
import org.wine.art.utils.handlers.CallbackDataHandler
import java.util.*

@Component
class PageManagement(
    private val userService: UserService
                    ) :
    CallbackDataHandler(
        AdminCommandName.PAGE_MANAGEMENT.text,
        "Управление страницами",
        EnumSet.of(Role.ADMIN, Role.USER)
                       ) {

    override fun handle(absSender: AbsSender, user: User, update: Update) {
        val pageStr = update.callbackQuery.data.substringAfter(".page_")
        val page = pageStr.toIntOrNull()


        if (page != null) {
            val chatId = user.id
            val certificates = getCertificates(chatId)

            if (certificates != null) {
                userService.getExisting(chatId).messageId?.let {
                    sendCertificates(
                        absSender, chatId.toString(), certificates,
                        it, page
                                    )
                }
            } else {
                return
            }
        }
    }

    private fun sendCertificates(
        sender: AbsSender,
        chatId: String,
        certificates: List<Certificate>,
        messageId: String,
        page: Int = 0
                                ) {
        val paginatedCertificates = certificates.drop(page * PAGE_SIZE).take(PAGE_SIZE)
        val messageText = buildCertificatesListMessage(paginatedCertificates)
        val keyboardMarkup = buildNavigationKeyboard(page, certificates.size)

        val sendMessage = EditMessageText.builder()
            .chatId(chatId)
            .text(messageText)
            .messageId(messageId.toInt())
            .parseMode("HTML")
            .replyMarkup(keyboardMarkup)
            .build()

        sender.execute(sendMessage)
    }
}