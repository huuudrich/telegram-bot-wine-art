package com.wineart.user.action

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Update
import com.wineart.model.certificate.CertificateStatus
import com.wineart.service.certificate.CertificateService
import com.wineart.service.certificate.arguments.CreateCertificateArg
import com.wineart.service.user.UserService
import com.wineart.utils.VoidAction
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import java.util.*

@Component
class PaymentProcessingAction(
    private val sendCertificateAction: VoidAction<Bot, Update>,
    private val certificateService: CertificateService,
    private val userService: UserService
                             ) : VoidAction<Bot, Update> {

    private val log = KotlinLogging.logger {}

    override fun execute(bot: Bot, argument: Update) {
        argument.message?.let { message ->
            if (message.successfulPayment != null) {
                val payment = message.successfulPayment
                if (payment != null) {
                    val chatId = argument.message?.chat?.id

                    log.info { "Успешный платеж: ${payment.invoicePayload}" }

                    chatId?.let {
                        CreateCertificateArg(
                            UUID.fromString(payment.providerPaymentChargeId),
                            it,
                            payment.totalAmount / 100,
                            CertificateStatus.ACTIVATED
                                            )
                    }?.let {
                        certificateService.create(it)
                        userService.addCertificate(it.chatId, it.paymentId)
                    }

                    sendCertificateAction.execute(bot, argument)
                }
            }
        }
    }
}