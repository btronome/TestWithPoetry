package com.example.testwithpoetry.presentation.authors

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.testwithpoetry.presentation.navigation.AuthorsNavGraph

@Composable
fun AuthorsScreen(viewModel: AuthorsViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    var topBarTitle by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(topBarTitle)
        },
        bottomBar = {
            BottomAppBar(navController, viewModel)
        }
    ) { innerPadding ->
        AuthorsNavGraph(
            navController = navController,
            viewModel = viewModel,
            onTitleChanged = { topBarTitle = it },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun TopAppBar(title: String) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun BottomAppBar(navController: NavController, viewModel: AuthorsViewModel) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.parent?.route
    NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
        viewModel.items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                icon = { },
                label = {
                    Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}
