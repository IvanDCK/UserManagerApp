package org.martarcas.usermanager.manager.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.martarcas.usermanager.manager.presentation.login.LoginViewModel


@Module
@ComponentScan("org.martarcas.usermanager.manager.presentation")
class PresentationModule