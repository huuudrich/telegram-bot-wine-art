package com.wineart.service.certificate

import com.wineart.model.certificate.Certificate
import com.wineart.service.certificate.arguments.CreateCertificateArg

interface CertificateService {
    fun create(argument: CreateCertificateArg): Certificate
}