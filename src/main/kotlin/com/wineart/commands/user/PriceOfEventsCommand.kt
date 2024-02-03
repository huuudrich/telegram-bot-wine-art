package com.wineart.commands.user

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.entities.Update
import com.github.kotlintelegrambot.entities.inputmedia.InputMediaPhoto
import com.github.kotlintelegrambot.entities.inputmedia.MediaGroup
import com.wineart.utils.VoidAction
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File

@Component
class PriceOfEventsCommand(private val sendCertificateAction: VoidAction) : VoidAction {
    @Value("\${files.path}")
    private lateinit var path: String
    override fun execute(bot: Bot, update: Update) {
        sendCertificateAction.execute(bot, update)

        val chatId = update.message?.chat?.id ?: return

        bot.sendMediaGroup(
            chatId = ChatId.fromId(chatId), mediaGroup = MediaGroup.from(media = getPricesPhotos()),
            replyToMessageId = update.message!!.messageId
                          )
    }

    private fun getPricesPhotos(): Array<InputMediaPhoto> {
        return arrayOf(
            InputMediaPhoto(
                TelegramFile.ByFile(
                    File("$path/price_1.jpg")
                                   ), caption = "price_1"
                           ),
            InputMediaPhoto(
                TelegramFile.ByFile(
                    File("$path/price_2.jpg")
                                   ), caption = "price_2"
                           ),
            InputMediaPhoto(
                TelegramFile.ByFile(
                    File("$path/price_3.jpg")
                                   ), caption = "price_3"
                           )
                      )
    }
}