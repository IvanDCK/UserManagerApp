package org.martarcas.usermanager.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.martarcas.usermanager.presentation.activity.ActivityScreen
import org.martarcas.usermanager.presentation.list.UserListScreenRoot
import org.martarcas.usermanager.presentation.list.UserListViewModel
import org.martarcas.usermanager.presentation.login.LoginScreen
import org.martarcas.usermanager.presentation.signup.SignUpScreen

@Composable
fun NavigationWrapper(shouldStartFromList: Boolean) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == "org.martarcas.usermanager.navigation.Destinations.List" || currentRoute == "org.martarcas.usermanager.navigation.Destinations.Activity") {
                BottomAppBar(
                    modifier = Modifier
                        .height(80.dp)
                ) {
                    NavigationBarItems.entries.forEach { item ->
                        NavigationBarItem(
                            modifier = Modifier
                                .padding(top = 10.dp),
                            selected = currentRoute == item.route.toString(),
                            label = { Text(text = item.label) },
                            onClick = {
                                navController.navigate(item.route)
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = "bottom_bar_icon"
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .padding(
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    top = 0.dp,
                    end = paddingValues.calculateEndPadding(LayoutDirection.Rtl),
                    bottom = paddingValues.calculateBottomPadding()
                ),
            navController = navController,
            startDestination = if (shouldStartFromList) Destinations.List else Destinations.Login
        ) {

            composable<Destinations.Login>(
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
                        navController.navigate(Destinations.SignUp)
                    },
                    navigateToList = {
                        navController.navigate(Destinations.List) {
                            popUpTo<Destinations.SignUp> { inclusive = true }
                        }

                    }
                )
            }

            composable<Destinations.SignUp> {
                SignUpScreen {
                    navController.navigate(Destinations.Login) {
                        popUpTo<Destinations.Login> { inclusive = true }
                    }
                }
            }

            composable<Destinations.List> {
                val listViewModel: UserListViewModel = koinViewModel()
                UserListScreenRoot(
                    viewModel = listViewModel,
                    navigateToLogin = {
                        navController.navigate(Destinations.Login)
                    }
                )
            }

            composable<Destinations.Activity> {
                ActivityScreen()
            }
        }
    }

}
