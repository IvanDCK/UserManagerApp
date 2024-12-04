package org.martarcas.usermanager.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import org.martarcas.usermanager.core.domain.DataStoreRepository
import org.martarcas.usermanager.manager.domain.model.Role
import org.martarcas.usermanager.manager.domain.model.user.User


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
                    role = Role.valueOf(preferences[USER_ROLE] ?: "NEW_USER")
                )
            }
    }
}