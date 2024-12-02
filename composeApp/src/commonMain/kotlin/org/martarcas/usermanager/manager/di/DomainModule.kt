package org.martarcas.usermanager.manager.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.martarcas.usermanager.manager.domain.use_cases.auth.LoginRequestUseCase
import org.martarcas.usermanager.manager.domain.use_cases.auth.SignUpRequestUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.UpdateRoleUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.GetUserByIdUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.GetAllUsersUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.UpdateUserUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.DeleteUserUseCase


val domainModule = module {
    singleOf(::UpdateRoleUseCase)
    singleOf(::GetUserByIdUseCase)
    singleOf(::GetAllUsersUseCase)
    singleOf(::UpdateUserUseCase)
    singleOf(::DeleteUserUseCase)

    singleOf(::LoginRequestUseCase)
    singleOf(::SignUpRequestUseCase)
}