package org.martarcas.usermanager

import android.app.Application
import com.martarcas.data.di.databaseModule
import com.martarcas.data.di.datastoreModule
import com.martarcas.data.remote.network.UserApi
import com.martarcas.data.remote.network.UserApiImpl
import com.martarcas.data.remote.repository.UserRepositoryImpl
import com.martarcas.domain.repository.UserRepository
import com.martarcas.domain.use_cases.activity.CreateActivityLogUseCase
import com.martarcas.domain.use_cases.auth.LoginRequestUseCase
import com.martarcas.domain.use_cases.auth.SignUpRequestUseCase
import com.martarcas.domain.use_cases.datastore.ReadRememberMeUseCase
import com.martarcas.domain.use_cases.datastore.ReadUserUseCase
import com.martarcas.domain.use_cases.datastore.SaveRememberMeAndUserUseCase
import com.martarcas.domain.use_cases.user.DeleteUserUseCase
import com.martarcas.domain.use_cases.user.GetAllUsersUseCase
import com.martarcas.domain.use_cases.user.UpdateRoleUseCase
import com.martarcas.domain.use_cases.user.UpdateUserUseCase
import com.martarcas.feature.presentation.list.UserListViewModel
import com.martarcas.feature.presentation.login.LoginViewModel
import com.martarcas.feature.presentation.signup.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.getKoinApplicationOrNull
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import org.martarcas.usermanager.di.platformModule
import org.martarcas.usermanager.presentation.AppViewModel

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
            signUpRequestUseCase = get(),
            createActivityLogUseCase = get()
        )
    }

}