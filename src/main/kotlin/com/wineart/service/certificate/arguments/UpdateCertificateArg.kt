package com.wineart.service.certificate.arguments

import com.wineart.model.certificate.CertificateStatus

data class UpdateCertificateArg(
    var cost: Int,
    var status: CertificateStatus
                               )