package org.martarcas.usermanager

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.getKoinApplicationOrNull
import org.koin.core.context.startKoin
import org.martarcas.usermanager.core.di.datastoreModule
import org.martarcas.usermanager.manager.di.databaseModule
import org.martarcas.usermanager.manager.di.platformModule
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import org.martarcas.usermanager.app.presentation.AppViewModel
import org.martarcas.usermanager.core.domain.use_cases.datastore.DataStoreUseCases
import org.martarcas.usermanager.manager.data.remote.network.UserApi
import org.martarcas.usermanager.manager.data.remote.network.UserApiImpl
import org.martarcas.usermanager.manager.data.remote.repository.UserRepositoryImpl
import org.martarcas.usermanager.manager.domain.UserRepository
import org.martarcas.usermanager.manager.domain.use_cases.auth.LoginRequestUseCase
import org.martarcas.usermanager.manager.domain.use_cases.auth.SignUpRequestUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.DeleteUserUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.GetAllUsersUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.UpdateRoleUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.UpdateUserUseCase
import org.martarcas.usermanager.manager.presentation.list.UserListViewModel
import org.martarcas.usermanager.manager.presentation.login.LoginViewModel
import org.martarcas.usermanager.manager.presentation.signup.SignUpViewModel

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

    singleOf(::DataStoreUseCases)
    singleOf(::LoginRequestUseCase)
    singleOf(::SignUpRequestUseCase)

    viewModel<UserListViewModel> {
        UserListViewModel(
            getAllUsersUseCase = get(),
            changeRoleUseCase = get(),
            updateUserUseCase = get(),
            deleteUserUseCase = get(),
            dataStoreUseCases = get()
        )
    }
    viewModel<AppViewModel> {
        AppViewModel(
            dataStoreUseCases = get()
        )
    }

    viewModel<LoginViewModel> {
        LoginViewModel(
            loginRequestUseCase = get(),
            dataStoreUseCases = get()
        )
    }

    viewModel<SignUpViewModel> {
        SignUpViewModel(
            signUpRequestUseCase = get()
        )
    }

}