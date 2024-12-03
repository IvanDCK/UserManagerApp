package org.martarcas.usermanager.manager.data.remote.network

import org.martarcas.usermanager.core.domain.Result
import org.martarcas.usermanager.core.domain.DataError
import org.martarcas.usermanager.manager.data.dto.UserPublicDto

interface UserApi {

    suspend fun getAllUsers(): Result<List<UserPublicDto>, DataError.Remote>

}