package org.wine.art.bot.user.message

import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.media.InputMedia
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.UserCommandName.PRICE_OF_EVENTS
import org.wine.art.model.user.Role
import org.wine.art.utils.handlers.BotMessageHandler
import java.io.InputStream
import java.util.*

@Component
class PriceOfEvents(private val resourceLoader: ResourceLoader) :
    BotMessageHandler(PRICE_OF_EVENTS.text, "Стоимость мероприятий", EnumSet.of(Role.ADMIN, Role.USER)) {

    override fun handle(absSender: AbsSender, message: Message, chat: Chat) {
        val chatId = chat.id.toString()

        val resource1 = resourceLoader.getResource("classpath:price/price_1.jpg").inputStream
        val resource2 = resourceLoader.getResource("classpath:price/price_2.jpg").inputStream
        val resource3 = resourceLoader.getResource("classpath:price/price_3.jpg").inputStream

        val medias: List<InputMedia> =
            listOf(
                createMediaPhoto(resource1),
                createMediaPhoto(resource2),
                createMediaPhoto(resource3)
                  )

        absSender.execute(
            SendMediaGroup.builder()
                .chatId(chatId)
                .medias(medias)
                .build()
                         )
    }

    fun createMediaPhoto(inputStream: InputStream): InputMediaPhoto {
        return InputMediaPhoto().apply {
            media = "attach://price.jpg"
            isNewMedia = true
            newMediaStream = inputStream
            mediaName = "price.jpg"
        }
    }
}