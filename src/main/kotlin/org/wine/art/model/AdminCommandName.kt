package org.wine.art.model

enum class AdminCommandName(val text: String) {
    START_ADMIN("admin"),
    GET_CERTIFICATE_INFO("Получить информацию по сертификату"),
    GET_ALL_CERTIFICATES("Получить все сертификаты"),
    ACTIVATE_CERTIFICATE("Активировать"),
    DEACTIVATE_CERTIFICATE("Заблокировать"),
    CHANGE_COST_CERTIFICATE("Изменить сумму счета"),
    PAGE_MANAGEMENT("Управление страницами")
}