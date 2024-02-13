package com.wineart.service.user

import com.wineart.model.user.User
import com.wineart.model.user.UserState
import com.wineart.service.user.arguments.CreateOrUpdateArg
import java.util.*

interface UserService {
    fun createOrUpdate(id: Long, argument: CreateOrUpdateArg): User
    fun updateState(id: Long, state: UserState): User
    fun updateCallBackQueryInfo(id: Long, info: String): User
    fun getExisting(id: Long): User
    fun resetState(id: Long): User
    fun resetCallBackQueryInfo(id: Long): User
    fun addCertificate(id: Long, certificateId: UUID): User
}