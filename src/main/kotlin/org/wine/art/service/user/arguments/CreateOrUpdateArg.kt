package org.wine.art.service.user.arguments

data class CreateOrUpdateArg(
    var telegramUsername: String? = null,
    var email: String? = null,
    var phone: String? = null
                            )
