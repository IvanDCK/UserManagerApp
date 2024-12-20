package com.martarcas.di

import com.martarcas.di.data.DataModule
import com.martarcas.di.data.databaseModule
import com.martarcas.di.data.datastoreModule
import com.martarcas.di.domain.DomainModule
import com.martarcas.di.presentation.PresentationModule
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
            PresentationModule().module
        )
    }
}