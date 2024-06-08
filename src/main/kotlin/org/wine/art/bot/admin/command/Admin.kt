package org.wine.art.bot.admin.command

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.AdminCommandName.*
import org.wine.art.utils.createMessageWithSimpleButtons

@Component
class Admin : BotCommand(START_ADMIN.text, "Админ панель") {

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val chatId = chat.id

        absSender.execute(
            createMessageWithSimpleButtons(
                chatId.toString(),
                "Добро пожаловать в админ панель",
                listOf(
                    listOf(
                        GET_CERTIFICATE_INFO.text,
                        GET_ALL_CERTIFICATES.text
                          )
                      )
                                          )
                         )
    }
}