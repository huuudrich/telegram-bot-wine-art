package com.wineart.admin.callback

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.CallbackQuery
import com.wineart.admin.action.GetCertificateInfoActionArg
import com.wineart.service.certificate.CertificateService
import com.wineart.service.user.UserService
import com.wineart.utils.VoidAction
import org.springframework.stereotype.Component
import java.util.*

@Component
class ActivateCertificateCallback(
    private val userService: UserService,
    private val certificateService: CertificateService,
    private val getCertificateInfoAction: VoidAction<Bot, GetCertificateInfoActionArg>
                                 ) : VoidAction<Bot, CallbackQuery> {
    override fun execute(bot: Bot, argument: CallbackQuery) {
        val chatId = argument.from.id

        val user = userService.getExisting(chatId)
        val paymentId = UUID.fromString(user.callBackQueryInfo)

        certificateService.activate(paymentId)

        getCertificateInfoAction.execute(
            bot,
            GetCertificateInfoActionArg(chatId, UUID.fromString(user.callBackQueryInfo))
                                        )
    }
}