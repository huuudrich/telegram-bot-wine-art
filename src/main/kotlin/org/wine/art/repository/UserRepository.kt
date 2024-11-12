package org.wine.art.repository

import org.springframework.data.repository.CrudRepository
import org.wine.art.model.user.User

interface UserRepository : CrudRepository<User, Long>