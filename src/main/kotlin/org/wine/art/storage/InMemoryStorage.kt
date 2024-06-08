import org.wine.art.model.certificate.Certificate
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

object InMemoryStorage {
    private val userCertificates: ConcurrentHashMap<Long, List<Certificate>> = ConcurrentHashMap()

    fun storeCertificates(userId: Long, certificates: List<Certificate>, ttlMinutes: Long): List<Certificate> {
        val sortedCertificates = certificates.sortedByDescending { it.createdAt }
        userCertificates[userId] = sortedCertificates

        Timer().schedule(TimeUnit.MINUTES.toMillis(ttlMinutes)) {
            userCertificates.remove(userId)
        }

        return sortedCertificates
    }

    fun getCertificates(userId: Long): List<Certificate>? {
        return userCertificates[userId]
    }
}