package org.martarcas.usermanager.manager.di


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.dsl.module
import org.martarcas.usermanager.core.data.HttpClientFactory
import org.martarcas.usermanager.manager.data.local.AppSettings
import org.martarcas.usermanager.manager.data.local.DataStoreRepositoryImpl
import org.martarcas.usermanager.manager.data.local.createDataStore
import org.martarcas.usermanager.manager.domain.DataStoreRepository


@Module
@ComponentScan("org.martarcas.usermanager.manager.data")
class DataModule

val databaseModule = module {
    single { HttpClientFactory.create(get()) }

    //singleOf(::UserRepositoryImpl).bind<UserRepository>()
    //singleOf(::UserApiImpl).bind<UserApi>()
}

