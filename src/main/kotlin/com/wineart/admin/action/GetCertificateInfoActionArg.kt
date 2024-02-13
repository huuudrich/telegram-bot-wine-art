package com.wineart.admin.action

import java.util.*

data class GetCertificateInfoActionArg(
    val chatId: Long,
    val paymentId: UUID
                                      )