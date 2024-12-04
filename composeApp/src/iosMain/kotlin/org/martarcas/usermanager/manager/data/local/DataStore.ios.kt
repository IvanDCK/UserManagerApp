package org.martarcas.usermanager.manager.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.annotation.Single

@OptIn(ExperimentalForeignApi::class)
actual fun createDataStore(context: Any?): DataStore<Preferences> {
    return AppSettings.getDataStore {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/$dataStoreFileName"
    }
}