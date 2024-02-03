package com.wineart.bot.utils

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Update
import lombok.NonNull

interface VoidMessage {

    fun execute(@NonNull bot: Bot, @NonNull update: Update)
}