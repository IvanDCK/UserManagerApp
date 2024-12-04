package org.martarcas.usermanager.core.data.local.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "settings.preferences_pb"

object AppSettings {
    private lateinit var dataStore: DataStore<Preferences>
    private val lock = SynchronizedObject()

    fun getDataStore(producePath: () -> String): DataStore<Preferences> {
        return synchronized(lock) {
            if (AppSettings::dataStore.isInitialized) {
                dataStore
            } else {
                PreferenceDataStoreFactory.createWithPath(
                    produceFile = { producePath().toPath() }
                ).also { dataStore = it }
            }
        }
    }
}