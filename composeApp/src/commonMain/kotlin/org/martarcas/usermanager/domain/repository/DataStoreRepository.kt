package org.martarcas.usermanager.domain.repository

import kotlinx.coroutines.flow.Flow
import org.martarcas.usermanager.domain.model.user.User

interface DataStoreRepository {

    suspend fun saveRememberMeAndUserData(rememberMe: Boolean, user: User)

    fun readRememberMe(): Flow<Boolean>

    fun readUserData(): Flow<User>
}