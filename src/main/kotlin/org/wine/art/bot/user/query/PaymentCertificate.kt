package org.wine.art.bot.user.query

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.Payload
import org.wine.art.utils.handlers.PreCheckoutQueryHandler

@Component
class PaymentCertificate : PreCheckoutQueryHandler(
    Payload.PAYMENT_CERTIFICATE,
    "Оплата сертификата"
                                                  ) {
    override fun handle(absSender: AbsSender, update: Update) {
        absSender.execute(
            AnswerPreCheckoutQuery.builder()
                .preCheckoutQueryId(update.preCheckoutQuery.id)
                .ok(true)
                .build()
                         )
    }
}