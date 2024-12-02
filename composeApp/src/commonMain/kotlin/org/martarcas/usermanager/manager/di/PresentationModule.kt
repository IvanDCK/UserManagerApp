package org.martarcas.usermanager.manager.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.martarcas.usermanager.manager.presentation.detail.UserDetailViewModel
import org.martarcas.usermanager.manager.presentation.list.UserListViewModel
import org.martarcas.usermanager.manager.presentation.login.LoginViewModel
import org.martarcas.usermanager.manager.presentation.signup.SignUpViewModel


@Module
@ComponentScan("org.martarcas.usermanager.manager.presentation")
class PresentationModule


/*
val presentationModule = module {
    viewModelOf(::SignUpViewModel)
    viewModelOf(::UserListViewModel)
    viewModelOf(::UserDetailViewModel)
    viewModelOf(::LoginViewModel)
}
 */
