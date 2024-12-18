package org.martarcas.usermanager.di

import com.martarcas.di.DataModule
import com.martarcas.di.DomainModule
import com.martarcas.di.databaseModule
import com.martarcas.di.datastoreModule
import com.martarcas.feature.di.PresentationModule
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