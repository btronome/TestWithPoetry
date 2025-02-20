package com.example.testwithpoetry.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testwithpoetry.presentation.authors.AuthorsScreen
import com.example.testwithpoetry.presentation.welcome.WelcomeScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Welcome.route
    ) {
        composable(Route.Welcome.route) {
            WelcomeScreen(
                hasSeenWelcomeScreen = {
                    navController.navigate(Route.Authors.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Route.Authors.route) {
            AuthorsScreen()
        }
    }
}
