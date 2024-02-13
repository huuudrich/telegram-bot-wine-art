package com.wineart.service.certificate

import com.wineart.model.certificate.Certificate
import com.wineart.model.certificate.CertificateStatus
import com.wineart.repository.CertificateRepository
import com.wineart.service.certificate.arguments.CreateCertificateArg
import com.wineart.service.certificate.arguments.UpdateCertificateArg
import lombok.NonNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class CertificateServiceImpl(private val repository: CertificateRepository) : CertificateService {
    override fun create(@NonNull argument: CreateCertificateArg): Certificate {
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

    override fun getExisting(@NonNull id: UUID): Certificate {
        return repository.findById(id).orElseThrow()
    }

    override fun activate(@NonNull id: UUID): Certificate {
        val certificate = getExisting(id)

        certificate.status = CertificateStatus.ACTIVATED

        return repository.save(certificate)
    }

    override fun deactivate(@NonNull id: UUID): Certificate {
        val certificate = getExisting(id)

        certificate.status = CertificateStatus.DEACTIVATED

        return repository.save(certificate)
    }

    override fun updateStatusAndCost(@NonNull id: UUID, @NonNull argument: UpdateCertificateArg): Certificate {
        val certificate = getExisting(id)

        certificate.status = argument.status
        certificate.cost = argument.cost

        return repository.save(certificate)
    }
}