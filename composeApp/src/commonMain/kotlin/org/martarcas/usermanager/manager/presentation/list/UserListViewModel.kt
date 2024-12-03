package org.martarcas.usermanager.manager.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import org.martarcas.usermanager.core.domain.onSuccess
import org.martarcas.usermanager.manager.domain.use_cases.user.GetAllUsersUseCase


@KoinViewModel
class UserListViewModel(
    @Provided private val getAllUsersUseCase: GetAllUsersUseCase

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