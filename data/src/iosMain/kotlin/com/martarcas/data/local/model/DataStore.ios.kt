package com.martarcas.data.local.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.martarcas.usermanager.data.local.model.AppSettings
import org.martarcas.usermanager.data.local.model.dataStoreFileName

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