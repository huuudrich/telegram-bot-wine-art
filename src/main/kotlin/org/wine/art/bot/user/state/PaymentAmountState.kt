package org.wine.art.bot.user.state

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.Payload
import org.wine.art.model.user.Role
import org.wine.art.model.user.UserState.USER_PAYMENT_AMOUNT_CERTIFICATE
import org.wine.art.service.user.UserService
import org.wine.art.utils.createMessage
import org.wine.art.utils.handlers.UserStatesHandler
import java.util.*

@Component
class PaymentAmountState(private val userService: UserService) : UserStatesHandler(
    USER_PAYMENT_AMOUNT_CERTIFICATE,
    "Состояние пользователя оплатить сертификат", EnumSet.of(Role.ADMIN, Role.USER)
                                                                                  ) {

    @Value("\${shop.provider.token}")
    private lateinit var shopToken: String

    private val log = KotlinLogging.logger {}

    override fun handle(absSender: AbsSender, message: Message, chat: Chat) {
        val chatId = chat.id.toString()
        val sum: Int = message.text.toIntOrNull() ?: return

        //todo вынести в проперти
        if (sum < 2500 || sum > 10000) {
            absSender.execute(createMessage(chatId, "Введите сумму в пределах лимита"))
            return
        }

        val prices = listOf(LabeledPrice("Руб", sum * 100))

        val sendInvoice = getInvoice(chatId, prices)

        try {
            val response = absSender.execute(sendInvoice)
            log.info { "Счет успешно отправлен: $response" }
        } catch (e: Exception) {
            log.error { "Ошибка при отправке счета: ${e.message}" }
        }

        userService.resetState(chat.id)
    }

    private fun getInvoice(
        chatId: String,
        prices: List<LabeledPrice>
                          ): SendInvoice {
        val sendInvoice = SendInvoice()
        sendInvoice.chatId = chatId
        sendInvoice.title = "Сертификат"
        sendInvoice.description = "Подарочный сертификат Wine Art на сумму:"
        sendInvoice.payload = Payload.PAYMENT_CERTIFICATE.toString()
        sendInvoice.providerToken = shopToken
        sendInvoice.startParameter = "startParam"
        sendInvoice.currency = "RUB"
        sendInvoice.prices = prices
        sendInvoice.isFlexible = false
        sendInvoice.replyMarkup = InlineKeyboardMarkup().apply {
            keyboard = listOf(
                listOf(
                    InlineKeyboardButton().apply {
                        text = "Оплатить"
                        pay = true
                    }
                      )
                             )
        }
        return sendInvoice
    }
}