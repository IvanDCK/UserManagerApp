package com.martarcas.di.data

import com.martarcas.data.remote.HttpClientFactory
import org.koin.dsl.module

val databaseModule = module {
    single { HttpClientFactory.create(get()) }
}