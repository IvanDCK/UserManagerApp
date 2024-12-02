package org.martarcas.usermanager.core.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Login) {

        composable<Login> {


        }


        composable<SignUp> {


        }


        composable<List> {


        }



        composable<UserDetail>(
            enterTransition = { slideInHorizontally { initialOffset ->
                initialOffset
            } },
            exitTransition = { slideOutHorizontally { initialOffset ->
                initialOffset
            } }
        ) { backStackEntry ->
            val id = backStackEntry.toRoute<UserDetail>().userId

        }

    }
}