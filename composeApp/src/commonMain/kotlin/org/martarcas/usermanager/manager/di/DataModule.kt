package org.martarcas.usermanager.manager.di


import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.martarcas.usermanager.core.data.HttpClientFactory
import org.martarcas.usermanager.manager.data.remote.network.UserApi
import org.martarcas.usermanager.manager.data.remote.network.UserApiImpl
import org.martarcas.usermanager.manager.data.repository.UserRepositoryImpl
import org.martarcas.usermanager.manager.domain.UserRepository

/*
@Module
@ComponentScan("org.martarcas.usermanager.manager.data")
class DataModule{

    @Single
    fun provideHttpClientFactory(engine: HttpClientEngine) = HttpClientFactory.create(engine)
}
 */

val dataModule = module {
    single { HttpClientFactory.create(get()) }

    singleOf(::UserRepositoryImpl).bind<UserRepository>()

    singleOf(::UserApiImpl).bind<UserApi>()
}

