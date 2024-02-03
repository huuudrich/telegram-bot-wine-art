package com.wineart.launcher

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.dispatcher.preCheckoutQuery
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.Update
import com.wineart.model.user.UserState
import com.wineart.service.user.UserService
import com.wineart.utils.CommandsName.*
import com.wineart.utils.VoidAction
import io.github.oshai.kotlinlogging.KotlinLogging
import lombok.NonNull
import org.springframework.stereotype.Service

/*
 * Ловит команды из бота
 */
@Service
class CommandsHandler(
    private val startCommand: VoidAction,
    private val buyCertificateCommand: VoidAction,
    private val priceOfEventsCommand: VoidAction,
    private val paymentProcessingAction: VoidAction,
    private val paymentAmountState: VoidAction,
    private val userService: UserService,
                     ) {

    private val log = KotlinLogging.logger {}

    fun execute(@NonNull processBot: Bot.Builder) {
        processBot.dispatch {
            text {
                initCommands(text, bot, update)
                initStates(bot, update)
            }
            preCheckoutQuery {
                log.info { "Получен preCheckoutQuery: ${preCheckoutQuery.id}" }
                bot.answerPreCheckoutQuery(preCheckoutQuery.id, ok = true)

                message { paymentProcessingAction.execute(bot, update) }
            }
        }
    }

    private fun initCommands(@NonNull text: String, @NonNull bot: Bot, @NonNull update: Update) {
        val commands = hashMapOf(
            START.s to { startCommand.execute(bot, update) },
            BUY_CERTIFICATE.s to { buyCertificateCommand.execute(bot, update) },
            PRICE_OF_EVENTS.s to { priceOfEventsCommand.execute(bot, update) }
                                )

        commands[text]?.invoke()
    }

    private fun initStates(@NonNull bot: Bot, @NonNull update: Update) {
        val chatId = update.message?.chat?.id
        var state: UserState? = null

        if (chatId != null) {
            val user = userService.getExisting(chatId)
            state = user.state
        }

        val states = hashMapOf(
            UserState.PAYMENT_AMOUNT_CERTIFICATE to { paymentAmountState.execute(bot, update) }
                              )

        states[state]?.invoke()
    }
}