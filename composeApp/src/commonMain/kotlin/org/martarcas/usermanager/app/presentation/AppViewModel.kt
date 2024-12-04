package org.martarcas.usermanager.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import org.martarcas.usermanager.core.domain.use_cases.datastore.DataStoreUseCases

@KoinViewModel
class AppViewModel(
    @Provided private val dataStoreUseCases: DataStoreUseCases
): ViewModel() {

    private val _shouldStartFromList = MutableStateFlow(true)
    val shouldStartFromList = _shouldStartFromList.asStateFlow()

    init {
        dataStoreUseCases.readRememberMeUseCase().onEach { shouldStartFromListScreen ->
            _shouldStartFromList.value = shouldStartFromListScreen
            delay(2000)
        }.launchIn(viewModelScope)
    }
}