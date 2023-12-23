package com.wineart.bot.launcher

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Update
import lombok.NonNull

interface CommandLauncher {
    fun start(@NonNull bot: Bot, @NonNull update: Update);

    fun signLesson(@NonNull bot: Bot, @NonNull update: Update);

    fun timeTable(@NonNull bot: Bot, @NonNull update: Update);

    fun buyCertificate(@NonNull bot: Bot, @NonNull update: Update);

    fun bookDate(@NonNull bot: Bot, @NonNull update: Update);
}