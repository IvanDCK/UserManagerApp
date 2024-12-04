package org.martarcas.usermanager.manager.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.annotation.Single

@Single
expect fun createDataStore(context: Any? = null): DataStore<Preferences>