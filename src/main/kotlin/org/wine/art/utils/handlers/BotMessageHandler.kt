package org.wine.art.utils.handlers

import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.user.Role
import java.util.*

abstract class BotMessageHandler(
    private val commandIdentifier: String,
    private val description: String,
    private val roles: EnumSet<Role>
                                ) {

    abstract fun handle(absSender: AbsSender, message: Message, chat: Chat)

    fun canHandle(message: Message, role: Role): Boolean {
        return if (roles.contains(role)) {
            message.text.equals(commandIdentifier, ignoreCase = true)
        } else {
            false
        }
    }
}