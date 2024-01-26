package com.wineart.bot.utils

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Update

interface VoidMessage {

    fun execute(bot: Bot, update: Update)
}