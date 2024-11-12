package org.wine.art.service.certificate.arguments

import org.wine.art.model.certificate.CertificateStatus
import java.util.*

data class CreateCertificateArg(
    val paymentId: UUID,
    val chatId: Long,
    var cost: Int,
    var status: CertificateStatus
                               )