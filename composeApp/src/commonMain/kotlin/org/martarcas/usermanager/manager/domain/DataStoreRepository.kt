package org.martarcas.usermanager.manager.domain

import kotlinx.coroutines.flow.Flow
import org.martarcas.usermanager.manager.domain.model.user.User

interface DataStoreRepository {

    suspend fun saveRememberMeAndUserData(rememberMe: Boolean, user: User)

    fun readRememberMe(): Flow<Boolean>

    fun readUserData(): Flow<User>
}