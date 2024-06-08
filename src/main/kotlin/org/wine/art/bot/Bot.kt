package org.wine.art.bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Update
import org.wine.art.model.user.User
import org.wine.art.service.user.UserService
import org.wine.art.service.user.arguments.CreateOrUpdateArg
import org.wine.art.utils.handlers.*

@Component
class Bot(
    private val userService: UserService,
    @Value("\${telegram.token}")
    token: String,
    commands: Set<BotCommand>,
    messageHandlers: Set<BotMessageHandler>,
    userStatesHandler: Set<UserStatesHandler>,
    preCheckoutQueryHandlers: Set<PreCheckoutQueryHandler>,
    paymentHandlers: Set<SuccessfulPaymentHandler>,
    callbackDataHandlers: Set<CallbackDataHandler>
         ) : TelegramLongPollingCommandBot(token) {

    private val messageHandler = mutableSetOf<BotMessageHandler>()
    private val statesHandler = mutableSetOf<UserStatesHandler>()
    private val preCheckoutQueryHandler = mutableSetOf<PreCheckoutQueryHandler>()
    private val paymentHandler = mutableSetOf<SuccessfulPaymentHandler>()
    private val callbackDataHandler = mutableSetOf<CallbackDataHandler>()

    init {
        registerAll(*commands.toTypedArray())
        messageHandler.addAll(messageHandlers)
        statesHandler.addAll(userStatesHandler)
        preCheckoutQueryHandler.addAll(preCheckoutQueryHandlers)
        paymentHandler.addAll(paymentHandlers)
        callbackDataHandler.addAll(callbackDataHandlers)
    }

    @Value("\${telegram.botName}")
    private val botName: String = ""

    override fun getBotUsername(): String = botName

    override fun processNonCommandUpdate(update: Update) {
        val message = update.message
        var user: User? = null

        if (update.hasMessage()) {
            user = getOrCreateUser(update.message.chatId, update)
            messageHandler.find { it.canHandle(message, user.role) }
                ?.handle(this, message, message.chat)
        }
        if (update.hasMessage() && user != null && user.state != null) {
            statesHandler.find { it.canHandle(user.state!!, user.role) }
                ?.handle(this, message, message.chat)
        }
        if (update.hasPreCheckoutQuery()) {
            preCheckoutQueryHandler.find { it.canHandle(update.preCheckoutQuery.invoicePayload) }
                ?.handle(this, update)
        }
        if (update.hasMessage() && message.hasSuccessfulPayment()) {
            paymentHandler.find { it.canHandle(message.successfulPayment.invoicePayload) }
                ?.handle(this, message, message.chat)
        }
        if (update.hasCallbackQuery()) {
            val userData = getOrCreateUser(update.callbackQuery.from.id, update)

            callbackDataHandler.find { it.canHandle(update, userData.role) }
                ?.handle(this, update.callbackQuery.from, update)
        }
    }

    private fun getOrCreateUser(chatId: Long, update: Update): User {
        val telegramUsername = update.message?.from?.userName

        return userService.getOrCreate(
            chatId, CreateOrUpdateArg(
                telegramUsername = telegramUsername
                                     )
                                      )
    }
}