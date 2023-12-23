package com.wineart.bot.launcher

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Update
import com.wineart.bot.utils.VoidMessage
import lombok.NonNull
import org.springframework.stereotype.Service

/*
 * Лаунчер для делегации запуска логики команд
 */
@Service
class CommandLauncherImp(
    val startCommand: VoidMessage,
    val startMarkups: VoidMessage
) : CommandLauncher {

    override fun start(@NonNull bot: Bot, @NonNull update: Update) {
        startCommand.execute(bot, update)
        startMarkups.execute(bot, update)
    }

    override fun signLesson(@NonNull bot: Bot, @NonNull update: Update) {
        startCommand.execute(bot, update)
        startMarkups.execute(bot, update)
    }

    override fun timeTable(@NonNull bot: Bot, @NonNull update: Update) {
        startCommand.execute(bot, update)
        startMarkups.execute(bot, update)
    }

    override fun buyCertificate(@NonNull bot: Bot, @NonNull update: Update) {
        startCommand.execute(bot, update)
        startMarkups.execute(bot, update)
    }

    override fun bookDate(@NonNull bot: Bot, @NonNull update: Update) {
        startCommand.execute(bot, update)
        startMarkups.execute(bot, update)
    }
}