package com.wineart.service.certificate

import com.wineart.model.certificate.Certificate
import com.wineart.repository.CertificateRepository
import com.wineart.service.certificate.arguments.CreateCertificateArg
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CertificateServiceImpl(private val repository: CertificateRepository) : CertificateService {
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
}