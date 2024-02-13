package com.wineart.repository

import com.wineart.model.certificate.Certificate
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface CertificateRepository : CrudRepository<Certificate, UUID>