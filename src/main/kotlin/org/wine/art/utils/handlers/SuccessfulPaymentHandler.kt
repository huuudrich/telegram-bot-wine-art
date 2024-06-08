package org.wine.art.utils.handlers

import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.Payload

abstract class SuccessfulPaymentHandler(
    private val payload: Payload,
    private val description: String
                                       ) {

    abstract fun handle(absSender: AbsSender, message: Message, chat: Chat)

    fun canHandle(payload: String): Boolean {
        return payload == this.payload.toString()
    }
}