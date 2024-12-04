package org.martarcas.usermanager.manager.di

import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(databaseModule, DataModule().module, DomainModule().module, PresentationModule().module, platformModule, datastoreModule)
        printLogger(Level.DEBUG)
    }
}