package com.wineart.repository

import com.wineart.model.user.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long>