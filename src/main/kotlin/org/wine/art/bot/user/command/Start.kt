package org.wine.art.bot.user.command

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.config.MessagesProperties
import org.wine.art.model.UserCommandName.*
import org.wine.art.utils.createMessageWithSimpleButtons

@Component
class Start(private val messagesProperties: MessagesProperties) : BotCommand(START.text, "Стартовая команда") {

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val chatId = chat.id.toString()

        absSender.execute(
            createMessageWithSimpleButtons(
                chatId,
                messagesProperties.start.command.message,
                listOf(
                    listOf(BUY_CERTIFICATE.text, PRICE_OF_EVENTS.text, GET_MY_CERTIFICATES.text)
                      )
                                          )
                         )
    }
}