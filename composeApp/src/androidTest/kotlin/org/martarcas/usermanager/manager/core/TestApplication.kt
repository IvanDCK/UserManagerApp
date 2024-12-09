package org.martarcas.usermanager.manager.core

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import org.martarcas.usermanager.app.di.AppModule
import org.martarcas.usermanager.core.di.datastoreModule
import org.martarcas.usermanager.manager.di.DataModule
import org.martarcas.usermanager.manager.di.DomainModule
import org.martarcas.usermanager.manager.di.PresentationModule
import org.martarcas.usermanager.manager.di.databaseModule
import org.martarcas.usermanager.manager.di.platformModule

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            //modules(testModule, platformModule, databaseModule, datastoreModule)
            modules(
                databaseModule,
                platformModule,
                datastoreModule,
                DataModule().module,
                DomainModule().module,
                PresentationModule().module,
                AppModule().module
            )
        }
    }
}