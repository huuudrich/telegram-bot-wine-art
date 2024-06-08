package org.wine.art.bot.admin.action

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.wine.art.model.AdminCommandName
import org.wine.art.service.certificate.CertificateService
import org.wine.art.service.user.UserService
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class BuildCertificateInfo(
    private val userService: UserService,
    private val certificateService: CertificateService,
                          ) {

    fun execute(paymentId: String, chatId: Long): SendMessage {
        val certificate = certificateService.getExisting(UUID.fromString(paymentId))

        val username = userService.getExisting(certificate.chatId).telegramUsername

        val keyboardMarkup = getKeyboard()

        return SendMessage.builder()
            .chatId(chatId)
            .text(
                "<b>Номер транзакции:</b> ${certificate.paymentId}\n" +
                        "<b>Никнейм покупателя:</b> @$username\n" +
                        "<b>Сумма на счету сертификата:</b> ${certificate.cost}\n" +
                        "<b>Статус сертификата:</b> ${certificate.status.s}\n" +
                        "<b>Дата покупки:</b> ${certificate.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}",
                 )
            .parseMode(ParseMode.HTML)
            .replyMarkup(keyboardMarkup)
            .build()
    }

    fun getKeyboard(): InlineKeyboardMarkup {
        val buttons = listOf(
            AdminCommandName.ACTIVATE_CERTIFICATE,
            AdminCommandName.DEACTIVATE_CERTIFICATE,
            AdminCommandName.CHANGE_COST_CERTIFICATE
                            ).map { command ->
            InlineKeyboardButton().apply {
                text = command.text
                callbackData = command.text
            }
        }

        val keyboardMarkup = InlineKeyboardMarkup().apply {
            keyboard = listOf(buttons)
        }

        return keyboardMarkup
    }
}