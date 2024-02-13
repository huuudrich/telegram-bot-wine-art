package com.wineart.service.user

import com.wineart.model.user.User
import com.wineart.model.user.UserState
import com.wineart.repository.UserRepository
import com.wineart.service.user.arguments.CreateOrUpdateArg
import lombok.NonNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val repository: UserRepository
                     ) : UserService {
    override fun createOrUpdate(@NonNull id: Long, @NonNull argument: CreateOrUpdateArg): User {
        val user = repository.findById(id).orElse(User(id))

        user.email = argument.email ?: user.email
        user.phone = argument.phone ?: user.phone
        user.telegramUsername = argument.telegramUsername ?: user.telegramUsername

        return repository.save(user)
    }

    override fun updateState(@NonNull id: Long, @NonNull state: UserState): User {
        val user = getExisting(id)

        user.state = state

        return repository.save(user)
    }

    override fun updateCallBackQueryInfo(@NonNull id: Long, @NonNull info: String): User {
        val user = getExisting(id)

        user.callBackQueryInfo = info

        return repository.save(user)
    }

    override fun getExisting(@NonNull id: Long): User {
        //TODO("Кастомный эсепшн")
        return repository.findById(id).orElseThrow()
    }

    override fun resetState(@NonNull id: Long): User {
        val user = getExisting(id)

        user.state = null

        return repository.save(user)
    }

    override fun resetCallBackQueryInfo(@NonNull id: Long): User {
        val user = getExisting(id)

        user.callBackQueryInfo = null

        return repository.save(user)
    }

    override fun addCertificate(@NonNull id: Long, @NonNull certificateId: UUID): User {
        val user = getExisting(id)

        user.certificateIds.add(certificateId)

        return repository.save(user)
    }
}