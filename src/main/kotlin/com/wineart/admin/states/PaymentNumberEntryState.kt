package com.wineart.admin.states

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Update
import com.wineart.admin.action.GetCertificateInfoActionArg
import com.wineart.utils.VoidAction
import org.springframework.stereotype.Component
import java.util.*

@Component
class PaymentNumberEntryState(
    private val getCertificateInfoAction: VoidAction<Bot, GetCertificateInfoActionArg>
                             ) : VoidAction<Bot, Update> {
    override fun execute(bot: Bot, argument: Update) {
        val chatId = argument.message?.chat?.id ?: return

        val text = argument.message!!.text ?: return

        val paymentId = UUID.fromString(text)

        getCertificateInfoAction.execute(bot, GetCertificateInfoActionArg(chatId, paymentId))
    }
}