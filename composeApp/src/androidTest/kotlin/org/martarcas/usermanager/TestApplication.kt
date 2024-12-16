package org.martarcas.usermanager

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.getKoinApplicationOrNull
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import org.martarcas.usermanager.data.remote.network.UserApi
import org.martarcas.usermanager.data.remote.network.UserApiImpl
import org.martarcas.usermanager.data.remote.repository.UserRepositoryImpl
import org.martarcas.usermanager.di.databaseModule
import org.martarcas.usermanager.di.datastoreModule
import org.martarcas.usermanager.di.platformModule
import org.martarcas.usermanager.domain.repository.UserRepository
import org.martarcas.usermanager.domain.use_cases.activity.CreateActivityLogUseCase
import org.martarcas.usermanager.domain.use_cases.auth.LoginRequestUseCase
import org.martarcas.usermanager.domain.use_cases.auth.SignUpRequestUseCase
import org.martarcas.usermanager.domain.use_cases.datastore.ReadRememberMeUseCase
import org.martarcas.usermanager.domain.use_cases.datastore.ReadUserUseCase
import org.martarcas.usermanager.domain.use_cases.datastore.SaveRememberMeAndUserUseCase
import org.martarcas.usermanager.domain.use_cases.user.DeleteUserUseCase
import org.martarcas.usermanager.domain.use_cases.user.GetAllUsersUseCase
import org.martarcas.usermanager.domain.use_cases.user.UpdateRoleUseCase
import org.martarcas.usermanager.domain.use_cases.user.UpdateUserUseCase
import org.martarcas.usermanager.presentation.app.AppViewModel
import org.martarcas.usermanager.presentation.list.UserListViewModel
import org.martarcas.usermanager.presentation.login.LoginViewModel
import org.martarcas.usermanager.presentation.signup.SignUpViewModel

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (getKoinApplicationOrNull() == null) {

            startKoin {
                androidContext(applicationContext)
                modules(
                    databaseModule,
                    platformModule,
                    datastoreModule,
                    testModule
                )
            }
        }

    }
}

val testModule = module {
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::UserApiImpl).bind<UserApi>()

    singleOf(::GetAllUsersUseCase)
    singleOf(::UpdateUserUseCase)
    singleOf(::UpdateRoleUseCase)
    singleOf(::DeleteUserUseCase)

    singleOf(::LoginRequestUseCase)
    singleOf(::SignUpRequestUseCase)

    singleOf(::SaveRememberMeAndUserUseCase)
    singleOf(::ReadUserUseCase)
    singleOf(::ReadRememberMeUseCase)
    singleOf(::CreateActivityLogUseCase)

    viewModel<UserListViewModel> {
        UserListViewModel(
            getAllUsersUseCase = get(),
            changeRoleUseCase = get(),
            updateUserUseCase = get(),
            deleteUserUseCase = get(),
            saveRememberMeAndUserUseCase = get(),
            readUserUseCase = get(),
            createActivityLogUseCase = get()
        )
    }
    viewModel<AppViewModel> {
        AppViewModel(
            readRememberMeUseCase = get()
        )
    }

    viewModel<LoginViewModel> {
        LoginViewModel(
            loginRequestUseCase = get(),
            saveRememberMeAndUserUseCase = get()
        )
    }

    viewModel<SignUpViewModel> {
        SignUpViewModel(
            signUpRequestUseCase = get()
        )
    }

}