package org.martarcas.usermanager.core.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.martarcas.usermanager.manager.presentation.list.UserListScreenRoot
import org.martarcas.usermanager.manager.presentation.list.UserListViewModel
import org.martarcas.usermanager.manager.presentation.login.LoginScreen
import org.martarcas.usermanager.manager.presentation.signup.SignUpScreen

@Composable
fun NavigationWrapper(shouldStartFromList: Boolean) {
    val navController = rememberNavController()

    val listViewModel: UserListViewModel = koinViewModel()

    NavHost(
        navController = navController,
        startDestination = if (shouldStartFromList) List else Login
    ) {

        composable<Login>(
            enterTransition = {
                slideInHorizontally { initialOffset ->
                    initialOffset
                }
            },
            exitTransition = {
                slideOutHorizontally { initialOffset ->
                    initialOffset
                }
            }
        ) {
            LoginScreen(
                navigateToSignup = {
                    navController.navigate(SignUp)
                },
                navigateToList = {
                    navController.navigate(List) {
                        popUpTo<SignUp> { inclusive = true }
                    }

                }
            )
        }

        composable<SignUp> {
            SignUpScreen {
                navController.navigate(Login) {
                    popUpTo<Login> { inclusive = true }
                }
            }
        }

        composable<List> {
            UserListScreenRoot(
                viewModel = listViewModel,
                navigateToLogin = {
                    navController.navigate(Login)
                }
            )

        }

    }
}