package org.wine.art.utils.handlers

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.Payload

abstract class PreCheckoutQueryHandler(
    private val payload: Payload,
    private val description: String
                                      ) {

    abstract fun handle(absSender: AbsSender, update: Update)

    fun canHandle(payload: String): Boolean {
        return payload == this.payload.toString()
    }
}