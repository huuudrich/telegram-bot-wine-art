package com.wineart.service.certificate

import com.wineart.model.certificate.Certificate
import com.wineart.service.certificate.arguments.CreateCertificateArg
import com.wineart.service.certificate.arguments.UpdateCertificateArg
import java.util.*

interface CertificateService {
    fun create(argument: CreateCertificateArg): Certificate
    fun getExisting(id: UUID): Certificate
    fun activate(id: UUID): Certificate
    fun deactivate(id: UUID): Certificate
    fun updateStatusAndCost(id: UUID, argument: UpdateCertificateArg): Certificate
}