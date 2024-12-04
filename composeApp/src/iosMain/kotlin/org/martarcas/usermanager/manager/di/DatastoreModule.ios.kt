package org.martarcas.usermanager.manager.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.martarcas.usermanager.manager.data.local.createDataStore

actual val datastoreModule: Module = module {
    single { createDataStore(null) }
}
