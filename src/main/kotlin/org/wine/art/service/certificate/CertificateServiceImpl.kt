package org.wine.art.service.certificate

import org.springframework.stereotype.Service
import org.wine.art.model.certificate.Certificate
import org.wine.art.model.certificate.CertificateStatus
import org.wine.art.repository.CertificateRepository
import org.wine.art.service.certificate.arguments.CreateCertificateArg
import org.wine.art.service.certificate.arguments.UpdateCertificateArg
import java.time.LocalDateTime
import java.util.*

@Service
class CertificateServiceImpl(
    private val repository: CertificateRepository
                            ) : CertificateService {
    override fun create(argument: CreateCertificateArg): Certificate {
        return repository.save(
            Certificate(
                argument.paymentId,
                argument.chatId,
                argument.cost,
                argument.status,
                LocalDateTime.now()
                       )
                              )
    }

    override fun getExisting(id: UUID): Certificate {
        return repository.findById(id).orElseThrow()
    }

    override fun activate(id: UUID): Certificate {
        val certificate = getExisting(id)

        certificate.status = CertificateStatus.ACTIVATED

        return repository.save(certificate)
    }

    override fun deactivate(id: UUID): Certificate {
        val certificate = getExisting(id)

        certificate.status = CertificateStatus.DEACTIVATED

        return repository.save(certificate)
    }

    override fun updateStatusAndCost(id: UUID, argument: UpdateCertificateArg): Certificate {
        val certificate = getExisting(id)

        certificate.status = argument.status
        certificate.cost = argument.cost

        return repository.save(certificate)
    }

    override fun getAll(): List<Certificate> {
        return repository.findAll().toList()
    }

    override fun getAllByChatId(chatId: Long): List<Certificate> {
        return repository.findAll().filter { certificate -> certificate.chatId == chatId }
    }
}