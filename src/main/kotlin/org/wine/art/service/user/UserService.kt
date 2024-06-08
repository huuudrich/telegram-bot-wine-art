package org.wine.art.service.user

import org.wine.art.model.user.User
import org.wine.art.model.user.UserState
import org.wine.art.service.user.arguments.CreateOrUpdateArg
import java.util.*

interface UserService {
    fun createOrUpdate(id: Long, argument: CreateOrUpdateArg): User
    fun getOrCreate(id: Long, argument: CreateOrUpdateArg): User
    fun updateState(id: Long, state: UserState): User
    fun updateCallBackQueryInfo(id: Long, info: String): User
    fun updateMessageId(id: Long, messageId: String): User
    fun getExisting(id: Long): User
    fun resetState(id: Long): User
    fun resetCallBackQueryInfo(id: Long): User
    fun resetMessageId(id: Long): User
    fun addCertificate(id: Long, certificateId: UUID): User
}