package org.wine.art.utils

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.AdminCommandName
import org.wine.art.model.certificate.Certificate
import java.time.format.DateTimeFormatter

object CertificateUtil {
    const val PAGE_SIZE = 5

    fun buildCertificatesListMessage(certificates: List<Certificate>): String {
        return certificates.joinToString("\n\n") { certificate ->
            "<b>Номер транзакции:</b> ${certificate.paymentId}\n" +
                    "<b>Сумма на счету сертификата:</b> ${certificate.cost}\n" +
                    "<b>Идентификатор пользователя:</b> ${certificate.chatId}\n" +
                    "<b>Статус сертификата:</b> ${certificate.status.s}\n" +
                    "<b>Дата покупки:</b> ${certificate.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}"
        }
    }

    fun buildNavigationKeyboard(page: Int, totalCertificates: Int): InlineKeyboardMarkup {
        val buttons = mutableListOf<List<InlineKeyboardButton>>()

        if (page > 0) {
            buttons.add(
                listOf(
                    InlineKeyboardButton().apply {
                        text = "⬅️ Назад"
                        callbackData = AdminCommandName.PAGE_MANAGEMENT.text + ".page_${page - 1}"
                    }
                      )
                       )
        }

        if ((page + 1) * PAGE_SIZE < totalCertificates) {
            buttons.add(
                listOf(
                    InlineKeyboardButton().apply {
                        text = "Вперед ➡️"
                        callbackData = AdminCommandName.PAGE_MANAGEMENT.text + ".page_${page + 1}"
                    }
                      )
                       )
        }

        return InlineKeyboardMarkup().apply {
            keyboard = buttons
        }
    }

    fun sendCertificates(
        sender: AbsSender,
        chatId: String,
        certificates: List<Certificate>,
        page: Int = 0
                        ): Message {
        val paginatedCertificates = certificates.drop(page * PAGE_SIZE).take(PAGE_SIZE)
        val messageText = buildCertificatesListMessage(paginatedCertificates)
        val keyboardMarkup = buildNavigationKeyboard(page, certificates.size)

        val sendMessage = SendMessage.builder()
            .chatId(chatId)
            .text(messageText)
            .parseMode("HTML")
            .replyMarkup(keyboardMarkup)
            .build()

        return sender.execute(sendMessage)
    }
}