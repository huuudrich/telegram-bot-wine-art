package com.wineart

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.dispatcher.preCheckoutQuery
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.CallbackQuery
import com.github.kotlintelegrambot.entities.Update
import com.wineart.admin.AdminCommandsName.*
import com.wineart.model.user.UserState
import com.wineart.model.user.UserState.*
import com.wineart.service.user.UserService
import com.wineart.user.UserCommandsName.*
import com.wineart.utils.VoidAction
import io.github.oshai.kotlinlogging.KotlinLogging
import lombok.NonNull
import org.springframework.stereotype.Service

/*
 * Ловит команды из бота
 */
@Service
class CommandsHandler(
    private val startUserCommand: VoidAction<Bot, Update>,
    private val buyCertificateCommand: VoidAction<Bot, Update>,
    private val priceOfEventsCommand: VoidAction<Bot, Update>,
    private val paymentProcessingAction: VoidAction<Bot, Update>,
    private val startAdminCommand: VoidAction<Bot, Update>,
    private val getCertificateInfoCommand: VoidAction<Bot, Update>,
    private val paymentNumberEntryState: VoidAction<Bot, Update>,
    private val paymentAmountState: VoidAction<Bot, Update>,
    private val changeCostCertificateEntryState: VoidAction<Bot, Update>,
    private val changeCostCertificateCallback: VoidAction<Bot, CallbackQuery>,
    private val activateCertificateCallback: VoidAction<Bot, CallbackQuery>,
    private val deactivateCertificateCallback: VoidAction<Bot, CallbackQuery>,
    private val userService: UserService,
                     ) {

    private val log = KotlinLogging.logger {}

    fun execute(@NonNull processBot: Bot.Builder) {
        processBot.dispatch {
            callbackQuery {
                if (callbackQuery.from.id == null) {
                    initCallBackQuery(callbackQuery, bot)
                }
            }
            text {
                val chatId = update.message?.chat?.id

                if (chatId == null) {
                    initAdminCommands(text, bot, update)
                    initAdminStates(bot, update)
                } else {
                    initCommands(text, bot, update)
                    initUserStates(bot, update)
                }
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
            START.s to { startUserCommand.execute(bot, update) },
            BUY_CERTIFICATE.s to { buyCertificateCommand.execute(bot, update) },
            PRICE_OF_EVENTS.s to { priceOfEventsCommand.execute(bot, update) }
                                )

        commands[text]?.invoke()
    }

    private fun initUserStates(@NonNull bot: Bot, @NonNull update: Update) {
        val chatId = update.message?.chat?.id
        var state: UserState? = null

        if (chatId != null) {
            val user = userService.getExisting(chatId)
            state = user.state
        }

        val states = hashMapOf(
            USER_PAYMENT_AMOUNT_CERTIFICATE to { paymentAmountState.execute(bot, update) }
                              )

        states[state]?.invoke()
    }

    private fun initAdminCommands(@NonNull text: String, @NonNull bot: Bot, @NonNull update: Update) {
        val commands = hashMapOf(
            START_ADMIN.s to { startAdminCommand.execute(bot, update) },
            GET_CERTIFICATE_INFO.s to { getCertificateInfoCommand.execute(bot, update) }
                                )

        commands[text]?.invoke()
    }

    private fun initAdminStates(@NonNull bot: Bot, @NonNull update: Update) {
        val chatId = update.message?.chat?.id
        var state: UserState? = null

        if (chatId != null) {
            val user = userService.getExisting(chatId)
            state = user.state
        }

        val states = hashMapOf(
            ADMIN_PAYMENT_NUMBER_ENTRY to { paymentNumberEntryState.execute(bot, update) },
            ADMIN_CHANGE_COST_CERTIFICATE_ENTRY to { changeCostCertificateEntryState.execute(bot, update) }
                              )

        states[state]?.invoke()
    }

    private fun initCallBackQuery(
        @NonNull callbackQuery: CallbackQuery,
        @NonNull bot: Bot
                                 ) {
        val data = callbackQuery.data

        val queries = hashMapOf(
            ACTIVATE_CERTIFICATE.s to { activateCertificateCallback.execute(bot, callbackQuery) },
            DEACTIVATE_CERTIFICATE.s to { deactivateCertificateCallback.execute(bot, callbackQuery) },
            CHANGE_COST_CERTIFICATE.s to { changeCostCertificateCallback.execute(bot, callbackQuery) }
                               )

        queries[data]?.invoke()
    }
}