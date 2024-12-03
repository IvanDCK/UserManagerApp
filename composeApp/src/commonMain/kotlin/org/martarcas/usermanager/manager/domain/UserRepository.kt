package org.martarcas.usermanager.manager.domain

import org.martarcas.usermanager.core.domain.Result
import org.martarcas.usermanager.core.domain.DataError
import org.martarcas.usermanager.manager.data.dto.UserPublicDto


interface UserRepository {

    suspend fun getAllUsers(): Result<List<UserPublicDto>, DataError.Remote>

}