package com.wineart.bot.model

/*
 * Текстовки сообщений доступных команд
 */
enum class MessageTexts(val s: String) {
    START("/start"),
    SIGN_LESSON("Записаться на занятие"),
    TIMETABLE("Расписание занятий"),
    BUY_CERTIFICATE("Купить сертификат"),
    BOOK_DATE("Забронировать дату под свое мероприятие");
}