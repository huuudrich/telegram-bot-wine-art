package com.wineart.service.user

import com.wineart.model.user.User
import com.wineart.model.user.UserState
import com.wineart.repository.UserRepository
import com.wineart.service.user.arguments.CreateUserArg
import com.wineart.service.user.arguments.UpdateUserArg
import lombok.NonNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(private val repository: UserRepository) : UserService {
    override fun create(@NonNull argument: CreateUserArg): User {
        return repository.save(
            User(
                argument.telegramId,
                argument.telegramUsername,
                )
                              )
    }

    override fun update(@NonNull id: Long, @NonNull argument: UpdateUserArg): User {
        val user = getExisting(id)

        user.email = argument.email ?: user.email
        user.phone = argument.phone ?: user.phone

        return repository.save(user)
    }

    override fun updateState(@NonNull id: Long, @NonNull state: UserState): User {
        val user = getExisting(id)

        user.state = state

        return repository.save(user)
    }

    override fun getExisting(@NonNull id: Long): User {
        //TODO("Кастомный эсепшн")
        return repository.findById(id).orElseThrow()
    }

    override fun statusReset(id: Long): User {
        val user = getExisting(id)

        user.state = null

        return repository.save(user)
    }

    override fun addCertificate(@NonNull id: Long, @NonNull certificateId: UUID): User {
        val user = getExisting(id)

        user.certificateIds.add(certificateId)

        return repository.save(user)
    }
}