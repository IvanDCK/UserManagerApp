package com.martarcas.di.data

import com.martarcas.data.local.model.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val datastoreModule: Module = module {
    single { createDataStore(null)}
}