package com.wineart.user

/*
 * Текстовки сообщений доступных команд
 */
enum class UserCommandsName(val s: String) {
    START("/start"),
    BUY_CERTIFICATE("Купить сертификат"),
    PRICE_OF_EVENTS("Стоимость мероприятий"),
    SIGN_UP_EVENT("Записаться на мероприятие")
}