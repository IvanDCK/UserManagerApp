package com.martarcas.domain.repository

import com.martarcas.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveRememberMeAndUserData(rememberMe: Boolean, user: User)

    fun readRememberMe(): Flow<Boolean>

    fun readUserData(): Flow<User>
}