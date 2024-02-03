package com.wineart.service.user

import com.wineart.model.user.User
import com.wineart.model.user.UserState
import com.wineart.service.user.arguments.CreateUserArg
import com.wineart.service.user.arguments.UpdateUserArg
import java.util.*

interface UserService {
    fun create(argument: CreateUserArg): User
    fun update(id: Long, argument: UpdateUserArg): User
    fun updateState(id: Long, state: UserState): User
    fun getExisting(id: Long): User
    fun statusReset(id: Long): User
    fun addCertificate(id: Long, certificateId: UUID): User
}