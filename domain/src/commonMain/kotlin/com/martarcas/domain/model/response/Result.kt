package com.martarcas.domain.model.response

sealed interface Result<out D, out E: com.martarcas.domain.model.response.Error> {
    data class Success<out D>(val data: D): com.martarcas.domain.model.response.Result<D, Nothing>
    data class Error<out E: com.martarcas.domain.model.response.Error>(val error: E):
        com.martarcas.domain.model.response.Result<Nothing, E>
}

inline fun <T, E: com.martarcas.domain.model.response.Error, R> com.martarcas.domain.model.response.Result<T, E>.map(map: (T) -> R): com.martarcas.domain.model.response.Result<R, E> {
    return when(this) {
        is com.martarcas.domain.model.response.Result.Error -> _root_ide_package_.com.martarcas.domain.model.response.Result.Error(
            error
        )
        is _root_ide_package_.com.martarcas.domain.model.response.Result.Success -> _root_ide_package_.com.martarcas.domain.model.response.Result.Success(
            map(data)
        )
    }
}

fun <T, E: _root_ide_package_.com.martarcas.domain.model.response.Error> _root_ide_package_.com.martarcas.domain.model.response.Result<T, E>.asEmptyDataResult(): _root_ide_package_.com.martarcas.domain.model.response.EmptyResult<E> {
    return map {  }
}

inline fun <T, E: _root_ide_package_.com.martarcas.domain.model.response.Error> _root_ide_package_.com.martarcas.domain.model.response.Result<T, E>.onSuccess(action: (T) -> Unit): _root_ide_package_.com.martarcas.domain.model.response.Result<T, E> {
    return when(this) {
        is _root_ide_package_.com.martarcas.domain.model.response.Result.Error -> this
        is _root_ide_package_.com.martarcas.domain.model.response.Result.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: _root_ide_package_.com.martarcas.domain.model.response.Error> _root_ide_package_.com.martarcas.domain.model.response.Result<T, E>.onError(action: (E) -> Unit): _root_ide_package_.com.martarcas.domain.model.response.Result<T, E> {
    return when(this) {
        is _root_ide_package_.com.martarcas.domain.model.response.Result.Error -> {
            action(error)
            this
        }
        is _root_ide_package_.com.martarcas.domain.model.response.Result.Success -> this
    }
}

typealias EmptyResult<E> = _root_ide_package_.com.martarcas.domain.model.response.Result<Unit, E>