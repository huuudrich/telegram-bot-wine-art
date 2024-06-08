package org.wine.art.utils.handlers

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.user.Role
import java.util.*

abstract class CallbackDataHandler(
    private val commandIdentifier: String,
    private val description: String,
    private val roles: EnumSet<Role>
                                  ) {

    abstract fun handle(absSender: AbsSender, user: User, update: Update)

    fun canHandle(update: Update, role: Role): Boolean {
        return if (roles.contains(role) && update.hasCallbackQuery()) {
            update.callbackQuery.data.contains(commandIdentifier, ignoreCase = true)
        } else {
            false
        }
    }
}