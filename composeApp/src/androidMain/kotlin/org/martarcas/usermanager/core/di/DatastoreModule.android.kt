package org.martarcas.usermanager.core.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import org.martarcas.usermanager.core.data.local.DataStoreRepositoryImpl
import org.martarcas.usermanager.core.data.local.model.createDataStore
import org.martarcas.usermanager.core.domain.DataStoreRepository
import org.martarcas.usermanager.core.domain.use_cases.datastore.DataStoreUseCases
import org.martarcas.usermanager.core.domain.use_cases.datastore.ReadRememberMeUseCase
import org.martarcas.usermanager.core.domain.use_cases.datastore.ReadUserUseCase
import org.martarcas.usermanager.core.domain.use_cases.datastore.SaveRememberMeAndUserUseCase

actual val datastoreModule: Module = module {
    single { createDataStore(androidContext()) }
    single<DataStoreRepository> { DataStoreRepositoryImpl(get()) }
    single { SaveRememberMeAndUserUseCase(get()) }
    single { ReadUserUseCase(get()) }
    single { ReadRememberMeUseCase(get()) }
    single { DataStoreUseCases(get(), get(), get()) }
}