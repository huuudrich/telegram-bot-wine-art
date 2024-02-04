package com.wineart.admin.action

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.wineart.admin.AdminCommandsName
import com.wineart.service.certificate.CertificateService
import com.wineart.service.user.UserService
import com.wineart.utils.VoidAction
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class GetCertificateInfoAction(
    private val userService: UserService,
    private val certificateService: CertificateService
                              ) : VoidAction<Bot, GetCertificateInfoActionArg> {
    override fun execute(bot: Bot, argument: GetCertificateInfoActionArg) {
        val chatId = argument.chatId

        val certificate = certificateService.getExisting(argument.paymentId)

        val username = userService.getExisting(certificate.chatId).telegramUsername

        val keyboardMarkup = createInlineKeyboard()

        bot.sendMessage(
            ChatId.fromId(chatId),
            "<b>Номер транзакции:</b> ${certificate.paymentId}\n" +
                    "<b>Никнейм покупателя:</b> @$username\n" +
                    "<b>Сумма на счету сертификата:</b> ${certificate.cost}\n" +
                    "<b>Статус сертификата:</b> ${certificate.status.s}\n" +
                    "<b>Дата покупки:</b> ${certificate.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}",
            parseMode = ParseMode.HTML,
            replyMarkup = keyboardMarkup
                       )

        userService.resetState(chatId)
        userService.updateCallBackQueryInfo(chatId, certificate.paymentId.toString())
    }

    private fun createInlineKeyboard(): InlineKeyboardMarkup {
        return InlineKeyboardMarkup.create(
            listOf(
                listOf(
                    InlineKeyboardButton.CallbackData(
                        text = AdminCommandsName.ACTIVATE_CERTIFICATE.s,
                        callbackData = AdminCommandsName.ACTIVATE_CERTIFICATE.s
                                                     ),
                    InlineKeyboardButton.CallbackData(
                        text = AdminCommandsName.DEACTIVATE_CERTIFICATE.s,
                        callbackData = AdminCommandsName.DEACTIVATE_CERTIFICATE.s
                                                     ),
                    InlineKeyboardButton.CallbackData(
                        text = AdminCommandsName.CHANGE_COST_CERTIFICATE.s,
                        callbackData = AdminCommandsName.CHANGE_COST_CERTIFICATE.s
                                                     )
                      )
                  )
                                          )
    }
}