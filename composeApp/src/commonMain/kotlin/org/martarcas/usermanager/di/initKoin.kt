package org.martarcas.usermanager.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            databaseModule,
            platformModule,
            datastoreModule,
            DataModule().module,
            DomainModule().module,
            PresentationModule().module,
        )
       // printLogger(Level.DEBUG)
    }
}