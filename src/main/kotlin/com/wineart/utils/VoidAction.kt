package com.wineart.utils

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Update
import lombok.NonNull

interface VoidAction {

    fun execute(@NonNull bot: Bot, @NonNull update: Update)
}