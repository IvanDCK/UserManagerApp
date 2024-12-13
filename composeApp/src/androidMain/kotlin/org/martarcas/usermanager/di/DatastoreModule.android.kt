package org.martarcas.usermanager.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import org.martarcas.usermanager.data.local.DataStoreRepositoryImpl
import org.martarcas.usermanager.data.local.model.createDataStore
import org.martarcas.usermanager.domain.model.repository.DataStoreRepository
import org.martarcas.usermanager.domain.use_cases.datastore.ReadRememberMeUseCase
import org.martarcas.usermanager.domain.use_cases.datastore.ReadUserUseCase
import org.martarcas.usermanager.domain.use_cases.datastore.SaveRememberMeAndUserUseCase

actual val datastoreModule: Module = module {
    single { createDataStore(androidContext()) }
    single<DataStoreRepository> { DataStoreRepositoryImpl(get()) }
    single { SaveRememberMeAndUserUseCase(get()) }
    single { ReadUserUseCase(get()) }
    single { ReadRememberMeUseCase(get()) }
}