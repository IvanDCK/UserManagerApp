package com.martarcas.di


import com.martarcas.data.remote.HttpClientFactory
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module


@Module
@ComponentScan("org.martarcas.data")
class DataModule

val databaseModule = module {
    single { HttpClientFactory.create(get()) }

    //singleOf(::UserRepositoryImpl).bind<UserRepository>()
    //singleOf(::UserApiImpl).bind<UserApi>()
}

