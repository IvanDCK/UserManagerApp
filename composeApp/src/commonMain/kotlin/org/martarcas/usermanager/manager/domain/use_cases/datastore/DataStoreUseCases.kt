package org.martarcas.usermanager.manager.domain.use_cases.datastore

import org.koin.core.annotation.Provided

data class DataStoreUseCases(
   @Provided val saveRememberMeAndUserUseCase: SaveRememberMeAndUserUseCase,
   @Provided val readRememberMeUseCase: ReadRememberMeUseCase,
   @Provided val readUserUseCase: ReadUserUseCase
)