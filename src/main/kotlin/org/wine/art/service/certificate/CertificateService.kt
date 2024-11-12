package org.wine.art.service.certificate

import org.wine.art.model.certificate.Certificate
import org.wine.art.service.certificate.arguments.CreateCertificateArg
import org.wine.art.service.certificate.arguments.UpdateCertificateArg
import java.util.*

interface CertificateService {
    fun create(argument: CreateCertificateArg): Certificate
    fun getExisting(id: UUID): Certificate
    fun activate(id: UUID): Certificate
    fun deactivate(id: UUID): Certificate
    fun updateStatusAndCost(id: UUID, argument: UpdateCertificateArg): Certificate
    fun getAll(): List<Certificate>
    fun getAllByChatId(chatId: Long): List<Certificate>
}