package org.martarcas.usermanager.core.domain.model

sealed interface DataError: Error {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN,
        CONFLICT,
    }

    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }
}