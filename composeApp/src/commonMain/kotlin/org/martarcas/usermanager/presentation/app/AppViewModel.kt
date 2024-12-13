package org.martarcas.usermanager.presentation.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import org.martarcas.usermanager.domain.use_cases.datastore.ReadRememberMeUseCase

@KoinViewModel
class AppViewModel(
    @Provided private val readRememberMeUseCase: ReadRememberMeUseCase,
): ViewModel() {

    private val _shouldStartFromList = MutableStateFlow(false)
    val shouldStartFromList = _shouldStartFromList.asStateFlow()

    private val _splashCondition = MutableStateFlow(true)
    val splashCondition = _splashCondition.asStateFlow()

    init {
        readRememberMeUseCase().onEach { shouldStartFromListScreen ->
            _shouldStartFromList.value = shouldStartFromListScreen
            delay(2000)
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}