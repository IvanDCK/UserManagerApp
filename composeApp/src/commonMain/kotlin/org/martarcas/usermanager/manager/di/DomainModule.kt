package org.martarcas.usermanager.manager.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

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