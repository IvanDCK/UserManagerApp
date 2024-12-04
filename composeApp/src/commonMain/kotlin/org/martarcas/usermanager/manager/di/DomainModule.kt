package org.martarcas.usermanager.manager.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import org.koin.core.annotation.Singleton
import org.koin.dsl.module
import org.martarcas.usermanager.manager.data.local.DataStoreRepositoryImpl
import org.martarcas.usermanager.manager.data.local.createDataStore
import org.martarcas.usermanager.manager.domain.DataStoreRepository
import org.martarcas.usermanager.manager.domain.use_cases.datastore.DataStoreUseCases
import org.martarcas.usermanager.manager.domain.use_cases.datastore.ReadRememberMeUseCase
import org.martarcas.usermanager.manager.domain.use_cases.datastore.ReadUserUseCase
import org.martarcas.usermanager.manager.domain.use_cases.datastore.SaveRememberMeAndUserUseCase

@Module
@ComponentScan("org.martarcas.usermanager.manager.domain")
class DomainModule

/*
val domainModule = module {
    singleOf(::UpdateRoleUseCase)
    singleOf(::GetUserByIdUseCase)
    singleOf(::GetAllUsersUseCase)
    singleOf(::UpdateUserUseCase)
    singleOf(::DeleteUserUseCase)

    singleOf(::LoginRequestUseCase)
    singleOf(::SignUpRequestUseCase)
}
 */