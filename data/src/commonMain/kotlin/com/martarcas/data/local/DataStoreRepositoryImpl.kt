package com.martarcas.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.martarcas.domain.model.user.Role
import com.martarcas.domain.model.user.User
import com.martarcas.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map


class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): DataStoreRepository {
    companion object {
        val REMEMBER_ME = booleanPreferencesKey("remember_me")
        val USER_ID = intPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_LAST_NAME = stringPreferencesKey("user_last_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PASSWORD = stringPreferencesKey("user_password")
        val USER_ROLE = stringPreferencesKey("user_role")
        val USER_AVATAR = stringPreferencesKey("user_avatar")
    }

    override suspend fun saveRememberMeAndUserData(rememberMe: Boolean, user: User) {
        try {
            dataStore.edit { preferences ->
                preferences[REMEMBER_ME] = rememberMe
                preferences[USER_ID] = user.id
                preferences[USER_NAME] = user.name
                preferences[USER_LAST_NAME] = user.surname
                preferences[USER_EMAIL] = user.email
                preferences[USER_PASSWORD] = user.password
                preferences[USER_ROLE] = user.role.toString()
                preferences[USER_AVATAR] = user.avatarId
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    override fun readRememberMe(): Flow<Boolean> {
        return dataStore.data
            .catch { emptyFlow<Boolean>() }
            .map { preferences ->
                preferences[REMEMBER_ME] ?: false
            }

    }
    override fun readUserData(): Flow<User> {
        return dataStore.data
            .catch { emptyFlow<User>() }
            .map { preferences ->
                User(
                    id = preferences[USER_ID] ?: 0,
                    name = preferences[USER_NAME] ?: "",
                    surname = preferences[USER_LAST_NAME] ?: "",
                    email = preferences[USER_EMAIL] ?: "",
                    password = preferences[USER_PASSWORD] ?: "",
                    role = Role.valueOf(preferences[USER_ROLE] ?: "NEW_USER"),
                    avatarId = preferences[USER_AVATAR] ?: ""
                )
            }
    }
}