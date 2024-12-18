package org.martarcas.usermanager.di

import com.martarcas.data.di.DataModule
import com.martarcas.data.di.databaseModule
import com.martarcas.data.di.datastoreModule
import com.martarcas.domain.di.DomainModule
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
            AppVMModule().module
        )
    }
}