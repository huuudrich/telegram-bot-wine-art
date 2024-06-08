package org.wine.art.bot.admin.query

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.AdminCommandName
import org.wine.art.model.user.Role
import org.wine.art.model.user.UserState
import org.wine.art.service.user.UserService
import org.wine.art.utils.createMessage
import org.wine.art.utils.handlers.CallbackDataHandler
import java.util.*

@Component
class ChangeCostCertificate(
    private val userService: UserService
                           ) :
    CallbackDataHandler(
        AdminCommandName.CHANGE_COST_CERTIFICATE.text,
        "Ввести сумму сертификата для изменения",
        EnumSet.of(Role.ADMIN)
                       ) {

    override fun handle(absSender: AbsSender, user: User, update: Update) {
        val chatId = user.id

        absSender.execute(
            createMessage(
                chatId.toString(),
                "Введите новую сумму (если сумма будет 0 - сертификат заблокируется)"
                         )
                         )

        userService.updateState(chatId, UserState.ADMIN_CHANGE_COST_CERTIFICATE_ENTRY)
    }
}