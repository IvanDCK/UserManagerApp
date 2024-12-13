package org.martarcas.usermanager.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.martarcas.usermanager.data.local.model.createDataStore

actual val datastoreModule: Module = module {
    single { createDataStore(null) }
}
