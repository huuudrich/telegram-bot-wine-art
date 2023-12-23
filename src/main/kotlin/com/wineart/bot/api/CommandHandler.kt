package com.wineart.bot.api

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.wineart.bot.launcher.CommandLauncher
import com.wineart.bot.model.MessageTexts.StartMarkupsText.*
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/*
 * Основной "получатель команд" и работа с сообщениями
 */
@Component
class CommandHandler(val launch: CommandLauncher) {

    @Value("\${auth.bot.token}")
    private lateinit var token: String

    @PostConstruct
    fun handler() {
        val bot = bot {
            token = "6926579582:AAFBQTGafqw6CSHzar9BdTXcpwRpO9ANmRc"
            dispatch {
                command(START.s) { launch.start(bot, update) }
                command(SIGN_LESSON.s) { launch.signLesson(bot, update) }
                command(TIMETABLE.s) { launch.timeTable(bot, update) }
                command(BUY_CERTIFICATE.s) { launch.buyCertificate(bot, update) }
                command(BOOK_DATE.s) { launch.bookDate(bot, update) }
            }
        }
        bot.startPolling()
    }
}