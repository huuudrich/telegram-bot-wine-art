package com.wineart.user.states

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.Update
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.entities.payments.LabeledPrice
import com.github.kotlintelegrambot.entities.payments.PaymentInvoiceInfo
import com.wineart.service.user.UserService
import com.wineart.utils.VoidAction
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.BigInteger

@Component
class PaymentAmountState(private val userService: UserService) : VoidAction<Bot, Update>
 {

    @Value("\${shop.provider.token}")
    private lateinit var shopToken: String

    private val log = KotlinLogging.logger {}
    override fun execute(bot: Bot, argument: Update) {
        val chatId = argument.message?.chat?.id ?: return

        val sum = argument.message!!.text?.toLong()

        if (sum == null || sum < 1000 || sum > 20000) {
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "Введите сумму в пределах лимита"
                           )
            return
        }

        val prices = listOf(LabeledPrice("Руб", BigInteger.valueOf(sum * 100)))

        val paymentInvoiceInfo = PaymentInvoiceInfo(
            title = "Сертификат",
            description = "Подарочный сертификат Wine Art на сумму:",
            payload = chatId.toString(),
            providerToken = shopToken,
            startParameter = "startParam",
            currency = "RUB",
            prices = prices,
            isFlexible = false,
                                                   )
        bot.sendInvoice(
            chatId = ChatId.fromId(chatId),
            paymentInvoiceInfo = paymentInvoiceInfo,
            replyMarkup = InlineKeyboardMarkup.createSingleButton(InlineKeyboardButton.Pay("Оплатить"))
                       ).fold(
            ifSuccess = { response -> log.info { "Счет успешно отправлен: $response" } },
            ifError = { error -> log.info { "Ошибка при отправке счета: ${error.get()}" } }
                             )

        userService.resetState(chatId)
    }
}