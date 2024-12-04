package org.martarcas.usermanager.manager.di

import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module
import org.martarcas.usermanager.app.di.AppModule
import org.martarcas.usermanager.core.di.datastoreModule

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
            AppModule().module
        )
        printLogger(Level.DEBUG)
    }
}