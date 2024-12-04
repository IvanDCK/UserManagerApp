package org.martarcas.usermanager.manager.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module
import org.martarcas.usermanager.core.data.local.model.createDataStore


actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single<DataStore<Preferences>> { createDataStore(get()) }
    }