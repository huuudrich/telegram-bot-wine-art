package org.wine.art.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.wine.art.model.certificate.Certificate
import java.util.*

@Repository
interface CertificateRepository : CrudRepository<Certificate, UUID>