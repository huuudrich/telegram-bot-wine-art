package com.wineart.model.user

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.util.UUID

@RedisHash("Users")
data class User(
    @Id
    val chatId: Long,
    var email: String? = null,
    var phone: String? = null,
    var telegramUsername: String? = null,
    var state: UserState? = null,
    var certificateIds: MutableList<UUID> = mutableListOf()
               ) : Serializable