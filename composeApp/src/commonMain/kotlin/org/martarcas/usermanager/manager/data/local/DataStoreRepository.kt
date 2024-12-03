package org.martarcas.usermanager.manager.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class DataStoreRepository(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val REMEMBER_ME = booleanPreferencesKey("remember_me")
    }

    suspend fun saveRememberMe(rememberMe: Boolean) {
        try {
            dataStore.edit { preferences ->
                preferences[REMEMBER_ME] = rememberMe
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    fun readRememberMe(): Flow<Boolean> {
        return dataStore.data
            .catch { emptyFlow<Boolean>() }
            .map { preferences ->
                preferences[REMEMBER_ME] ?: false
            }

    }
}