package org.martarcas.usermanager.manager.di


import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.martarcas.usermanager.core.data.HttpClientFactory
import org.martarcas.usermanager.manager.data.local.AppSettings


@Module
@ComponentScan("org.martarcas.usermanager.manager.data")
class DataModule


val databaseModule = module {
    single { HttpClientFactory.create(get()) }

    //singleOf(::UserRepositoryImpl).bind<UserRepository>()

    //singleOf(::UserApiImpl).bind<UserApi>()
}

