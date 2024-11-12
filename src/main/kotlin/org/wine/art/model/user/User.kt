package org.wine.art.model.user

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.util.*

@RedisHash("Users")
data class User(
    @Id
    val chatId: Long,
    var email: String? = null,
    var phone: String? = null,
    var telegramUsername: String? = null,
    var state: UserState? = null,
    var callBackQueryInfo: String? = null,
    var certificateIds: MutableList<UUID> = mutableListOf(),
    var messageId: String? = null,
    val role: Role = Role.USER
               ) : Serializable