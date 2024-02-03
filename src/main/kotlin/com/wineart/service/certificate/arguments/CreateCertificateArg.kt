package com.wineart.service.certificate.arguments

import com.wineart.model.certificate.CertificateStatus
import java.util.*

data class CreateCertificateArg(
    val paymentId: UUID,
    val chatId: Long,
    var cost: Int,
    var status: CertificateStatus
                               )