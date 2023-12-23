package com.wineart.bot.model

/*
 * Текстовки сообщений доступных команд
 */
class MessageTexts {
    enum class StartMarkupsText(val s: String) {
        START("start"),
        SIGN_LESSON("Записаться на занятие"),
        TIMETABLE("Расписание занятий"),
        BUY_CERTIFICATE("Купить сертификат"),
        BOOK_DATE("Забронировать дату под свое мероприятие");
    }
}