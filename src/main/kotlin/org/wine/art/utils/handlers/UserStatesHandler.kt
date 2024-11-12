package org.wine.art.utils.handlers

import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.user.Role
import org.wine.art.model.user.UserState
import java.util.*

abstract class UserStatesHandler(
    private val userState: UserState,
    private val description: String,
    private val roles: EnumSet<Role>
                                ) {

    abstract fun handle(absSender: AbsSender, message: Message, chat: Chat)

    fun canHandle(userState: UserState, role: Role): Boolean {
        return if (roles.contains(role)) {
            userState == this.userState
        } else {
            false
        }
    }
}