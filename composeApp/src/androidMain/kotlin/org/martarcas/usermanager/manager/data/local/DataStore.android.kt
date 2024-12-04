package org.martarcas.usermanager.manager.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.annotation.Single

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    require(
        value = context is Context,
        lazyMessage = { "Context object is required. "}
    )
    return AppSettings.getDataStore(
        producePath = {
            context.filesDir
                .resolve(dataStoreFileName)
                .absolutePath
        }
    )
}