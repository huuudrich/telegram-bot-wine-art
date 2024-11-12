package org.wine.art.service.certificate.arguments

import org.wine.art.model.certificate.CertificateStatus

data class UpdateCertificateArg(
    var cost: Int,
    var status: CertificateStatus
                               )