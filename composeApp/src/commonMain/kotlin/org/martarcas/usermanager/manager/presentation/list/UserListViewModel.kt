package org.martarcas.usermanager.manager.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.martarcas.usermanager.core.domain.onSuccess
import org.martarcas.usermanager.manager.domain.use_cases.user.DeleteUserUseCase
import org.martarcas.usermanager.manager.domain.use_cases.user.GetAllUsersUseCase


@KoinViewModel
class UserListViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,

): ViewModel() {

    init {
        viewModelScope.launch {
            getAllUsersUseCase.invoke().onSuccess { users ->
                users.forEach {
                    println(it)
                }
            }



        }
    }
}