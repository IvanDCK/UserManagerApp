package org.martarcas.usermanager.di


import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.martarcas.usermanager.data.remote.HttpClientFactory


@Module
@ComponentScan("org.martarcas.usermanager.data")
class DataModule

val databaseModule = module {
    single { HttpClientFactory.create(get()) }

    //singleOf(::UserRepositoryImpl).bind<UserRepository>()
    //singleOf(::UserApiImpl).bind<UserApi>()
}

