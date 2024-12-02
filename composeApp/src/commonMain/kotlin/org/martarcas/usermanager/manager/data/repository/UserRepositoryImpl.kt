package org.martarcas.usermanager.manager.data.repository

import org.koin.core.annotation.Single
import org.martarcas.usermanager.manager.data.remote.network.UserApi
import org.martarcas.usermanager.manager.domain.UserRepository

class UserRepositoryImpl(
    private val userApi: UserApi
): UserRepository {

}