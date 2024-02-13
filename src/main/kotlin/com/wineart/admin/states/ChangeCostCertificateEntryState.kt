package com.wineart.admin.states

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Update
import com.wineart.admin.action.GetCertificateInfoActionArg
import com.wineart.model.certificate.CertificateStatus
import com.wineart.service.certificate.CertificateService
import com.wineart.service.certificate.arguments.UpdateCertificateArg
import com.wineart.service.user.UserService
import com.wineart.utils.VoidAction
import org.springframework.stereotype.Component
import java.util.*

@Component
class ChangeCostCertificateEntryState(
    private val certificateService: CertificateService,
    private val userService: UserService,
    private val getCertificateInfoAction: VoidAction<Bot, GetCertificateInfoActionArg>
                                     ) : VoidAction<Bot, Update> {
    override fun execute(bot: Bot, argument: Update) {
        val chatId = argument.message?.chat?.id ?: return

        val newSum = argument.message!!.text?.toInt()

        if (newSum == null || newSum < 0 || newSum > 10000) {
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "Сумма не может быть меньше 0 или больше 10000"
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

        getCertificateInfoAction.execute(bot, GetCertificateInfoActionArg(chatId, paymentId))
    }
}