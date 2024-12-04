package org.martarcas.usermanager.manager.domain.use_cases.datastore

import org.koin.core.annotation.Single

@Single
data class DataStoreUseCases(
    val saveRememberMeAndUserUseCase: SaveRememberMeAndUserUseCase,
    val readRememberMeUseCase: ReadRememberMeUseCase,
    val readUserUseCase: ReadUserUseCase
)