package org.martarcas.usermanager.manager.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.martarcas.usermanager.domain.model.repository.DataStoreRepository
import org.martarcas.usermanager.domain.model.user.Role
import org.martarcas.usermanager.domain.model.user.User

class FakeDataStoreRepositoryImpl: DataStoreRepository {

    private val dataStore: MutableMap<String, Any> = mutableMapOf()

    companion object {

        const val REMEMBER_ME = "remember_me"
        const val USER_ID = "user_id"
        const val USER_NAME = "user_name"
        const val USER_LAST_NAME = "user_last_name"
        const val USER_EMAIL = "user_email"
        const val USER_PASSWORD = "user_password"
        const val USER_ROLE = "user_role"
    }

    override suspend fun saveRememberMeAndUserData(rememberMe: Boolean, user: User) {
        try {
            dataStore[REMEMBER_ME] = rememberMe
            dataStore[USER_ID] = user.id
            dataStore[USER_NAME] = user.name
            dataStore[USER_LAST_NAME] = user.surname
            dataStore[USER_EMAIL] = user.email
            dataStore[USER_PASSWORD] = user.password
            dataStore[USER_ROLE] = user.role.toString()
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    override fun readRememberMe(): Flow<Boolean> {
        return flow {
            emit(dataStore[REMEMBER_ME] as? Boolean ?: false)
        }
    }

    override fun readUserData(): Flow<User> {
        return flow {
            emit(
                User(
                    id = dataStore[USER_ID] as? Int ?: 0,
                    name = dataStore[USER_NAME] as? String ?: "",
                    surname = dataStore[USER_LAST_NAME] as? String ?: "",
                    email = dataStore[USER_EMAIL] as? String ?: "",
                    password = dataStore[USER_PASSWORD] as? String ?: "",
                    role = Role.valueOf(dataStore[USER_ROLE] as? String ?: "NEW_USER")
                )
            )
        }
    }
}