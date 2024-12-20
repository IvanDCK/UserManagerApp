package com.martarcas.di.data

import com.martarcas.data.local.DataStoreRepositoryImpl
import com.martarcas.data.local.model.createDataStore
import com.martarcas.domain.repository.DataStoreRepository
import com.martarcas.domain.use_cases.datastore.ReadRememberMeUseCase
import com.martarcas.domain.use_cases.datastore.ReadUserUseCase
import com.martarcas.domain.use_cases.datastore.SaveRememberMeAndUserUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val datastoreModule: Module = module {
    single { createDataStore(androidContext()) }
    single<DataStoreRepository> { DataStoreRepositoryImpl(get()) }
    single { SaveRememberMeAndUserUseCase(get()) }
    single { ReadUserUseCase(get()) }
    single { ReadRememberMeUseCase(get()) }
}