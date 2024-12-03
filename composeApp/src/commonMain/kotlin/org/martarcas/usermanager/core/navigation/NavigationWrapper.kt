package org.martarcas.usermanager.core.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.martarcas.usermanager.manager.domain.model.Role
import org.martarcas.usermanager.manager.domain.model.user.User
import org.martarcas.usermanager.manager.presentation.list.UserListScreenRoot
import org.martarcas.usermanager.manager.presentation.list.UserListViewModel

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    val listViewModel: UserListViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = List) {

        composable<Login>(
            enterTransition = { slideInHorizontally { initialOffset ->
            initialOffset
        } },
            exitTransition = { slideOutHorizontally { initialOffset ->
                initialOffset
            } }
        ) {


        }

        composable<SignUp> {


        }


        composable<List> {
            UserListScreenRoot(
                viewModel = listViewModel,
                loggedUser = User(1, "Admin", "Admiin", "admin@admin", "12345678", Role.CEO)
            )

        }


    }
}