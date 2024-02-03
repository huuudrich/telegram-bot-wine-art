package com.wineart.service.user.arguments

data class CreateUserArg(
    val telegramId: Long,
    var telegramUsername: String? = null,
                        )