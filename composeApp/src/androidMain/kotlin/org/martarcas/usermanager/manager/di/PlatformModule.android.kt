package org.martarcas.usermanager.manager.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import org.martarcas.usermanager.manager.data.local.AppSettings
import org.martarcas.usermanager.manager.data.local.createDataStore


actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single<DataStore<Preferences>> { createDataStore(get()) }
    }