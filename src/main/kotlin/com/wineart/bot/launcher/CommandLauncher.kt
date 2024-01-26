package com.wineart.bot.launcher

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.text
import com.wineart.bot.model.MessageTexts.*
import com.wineart.bot.utils.VoidMessage
import lombok.NonNull
import org.springframework.stereotype.Service

/*
 * Лаунчер для делегации запуска логики команд
 */
@Service
class CommandLauncher(
    val startCommand: VoidMessage
                     ) {

    fun execute(@NonNull processBot: Bot.Builder) {
        processBot.dispatch {
            text {
                val map = hashMapOf(
                    START.s to {
                        startCommand.execute(bot, update)
                    },
                    SIGN_LESSON.s to {
                    },
                    TIMETABLE.s to {
                    },
                    BUY_CERTIFICATE.s to {
                    },
                    BOOK_DATE.s to {
                    }
                                   )
                map[text]?.invoke()
            }
        }
    }
}