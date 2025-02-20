package com.example.testwithpoetry.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.testwithpoetry.presentation.account.ProfileScreen
import com.example.testwithpoetry.presentation.authors.AuthorDetailsScreen
import com.example.testwithpoetry.presentation.authors.AuthorsViewModel
import com.example.testwithpoetry.presentation.authors.AuthorsListScreen

@Composable
fun AuthorsNavGraph(
    navController: NavHostController,
    viewModel: AuthorsViewModel,
    onTitleChanged: (String) -> Unit,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        route = Route.Authors.route,
        startDestination = Route.Poetry.route,
        modifier = modifier
    ) {
        addPoetryRoute(
            navController = navController,
            viewModel = viewModel,
            onTitleChanged = {
                onTitleChanged(it)
            }
        )
        addAccountRoute(
            onTitleChanged = {
                onTitleChanged(it)
            }
        )
    }
}

private fun NavGraphBuilder.addPoetryRoute(
    navController: NavController,
    viewModel: AuthorsViewModel,
    onTitleChanged: (String) -> Unit
) {
    navigation(
        route = Route.Poetry.route,
        startDestination = Route.AuthorsList.route
    ) {
        composable(route = Route.AuthorsList.route) {
            AuthorsListScreen(
                navController = navController,
                viewModel = viewModel,
                onTitleChanged = {
                    onTitleChanged(it)
                },
                onAuthorClick = { author ->
                    navController.navigate(Route.AuthorDetails.createRoute(author))
                }
            )
        }
        composable(
            route = Route.AuthorDetails.route,
            arguments = listOf(
                navArgument("author") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val author = entry.arguments?.getString("author") ?: ""
            onTitleChanged(author)
            AuthorDetailsScreen(author)
        }
    }
}

private fun NavGraphBuilder.addAccountRoute(onTitleChanged: (String) -> Unit) {
    navigation(
        route = Route.Account.route,
        startDestination = Route.Profile.route
    ) {
        composable(route = Route.Profile.route) {
            ProfileScreen(onTitleChanged = { onTitleChanged(it) })
        }
    }
}