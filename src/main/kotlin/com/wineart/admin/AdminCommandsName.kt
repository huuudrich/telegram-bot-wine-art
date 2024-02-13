package com.wineart.admin

enum class AdminCommandsName(val s: String)  {
    START_ADMIN("/start"),
    GET_CERTIFICATE_INFO("Получить информацию по сертификату"),
    ACTIVATE_CERTIFICATE("Активировать"),
    DEACTIVATE_CERTIFICATE("Заблокировать"),
    CHANGE_COST_CERTIFICATE("Изменить сумму счета");
}