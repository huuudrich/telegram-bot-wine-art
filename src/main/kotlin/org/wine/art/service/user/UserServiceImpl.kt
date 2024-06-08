package org.wine.art.service.user

import org.springframework.stereotype.Service
import org.wine.art.model.user.User
import org.wine.art.model.user.UserState
import org.wine.art.repository.UserRepository
import org.wine.art.service.user.arguments.CreateOrUpdateArg
import java.util.*

@Service
class UserServiceImpl(
    private val repository: UserRepository
                     ) : UserService {

    override fun createOrUpdate(id: Long, argument: CreateOrUpdateArg): User {
        val user = repository.findById(id).orElse(User(id))

        user.email = argument.email ?: user.email
        user.phone = argument.phone ?: user.phone
        user.telegramUsername = argument.telegramUsername ?: user.telegramUsername

        return repository.save(user)
    }

    override fun getOrCreate(id: Long, argument: CreateOrUpdateArg): User {
        val user = repository.findById(id)

        if (user.isEmpty) {
            val entity = User(id)
            entity.email = argument.email ?: entity.email
            entity.phone = argument.phone ?: entity.phone

            return repository.save(entity)
        }

        return user.get()
    }

    override fun updateState(id: Long, state: UserState): User {
        val user = getExisting(id)
        user.state = state
        return repository.save(user)
    }

    override fun updateCallBackQueryInfo(id: Long, info: String): User {
        val user = getExisting(id)
        user.callBackQueryInfo = info
        return repository.save(user)
    }

    override fun updateMessageId(id: Long, messageId: String): User {
        val user = getExisting(id)
        user.messageId = messageId
        return repository.save(user)
    }

    override fun getExisting(id: Long): User {
        //TODO("Кастомный эсепшн")
        return repository.findById(id).orElseThrow()
    }

    override fun resetState(id: Long): User {
        val user = getExisting(id)
        user.state = null
        return repository.save(user)
    }

    override fun resetCallBackQueryInfo(id: Long): User {
        val user = getExisting(id)
        user.callBackQueryInfo = null
        return repository.save(user)
    }

    override fun resetMessageId(id: Long): User {
        val user = getExisting(id)
        user.messageId = null
        return repository.save(user)
    }

    override fun addCertificate(id: Long, certificateId: UUID): User {
        val user = getExisting(id)
        user.certificateIds.add(certificateId)
        return repository.save(user)
    }
}