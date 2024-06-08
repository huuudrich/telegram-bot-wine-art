package org.wine.art.model.certificate

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@RedisHash("Certificates")
data class Certificate(
    @Id
    val paymentId: UUID,
    val chatId: Long,
    var cost: Int,
    var status: CertificateStatus,
    val createdAt: LocalDateTime
                      ) : Serializable