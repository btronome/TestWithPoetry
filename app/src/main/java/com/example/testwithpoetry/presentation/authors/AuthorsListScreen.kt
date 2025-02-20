package com.example.testwithpoetry.presentation.authors

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.testwithpoetry.R
import com.example.testwithpoetry.presentation.components.LoadingIndicator
import com.example.testwithpoetry.presentation.navigation.Route
import com.example.testwithpoetry.presentation.state.UiState
import com.example.testwithpoetry.utils.ShowToast

@Composable
fun AuthorsListScreen(
    navController: NavController,
    viewModel: AuthorsViewModel,
    onTitleChanged: (String) -> Unit,
    onAuthorClick: (String) -> Unit
) {
    val userName by viewModel.userName.collectAsState()
    val authorsState by viewModel.authorsState.collectAsState()
    val favoriteAuthors by viewModel.favoriteAuthors.collectAsState()
    onTitleChanged(getTopBarTitle(userName))

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isScreenVisible = navBackStackEntry?.destination?.route == Route.AuthorsList.route
    if (isScreenVisible) {
        LaunchedEffect(Unit) {
            viewModel.fetchAuthors()
        }

        when (authorsState) {
            is UiState.Loading -> {
                LoadingIndicator()
            }

            is UiState.Success -> {
                AuthorsList(authorsState, favoriteAuthors, viewModel, onAuthorClick)
            }

            is UiState.Error -> {
                ShowToast(message = (authorsState as UiState.Error).message)
            }
        }
    }
}

@Composable
private fun AuthorsList(
    state: UiState<List<String>?>,
    favoriteAuthors: List<String>,
    viewModel: AuthorsViewModel,
    onAuthorClick: (String) -> Unit
) {
    val context = LocalContext.current
    val addedAuthorMessage = stringResource(R.string.added_favorite_author)
    val authors = (state as UiState.Success<List<String>?>).data

    authors?.let {
        LazyColumn {
            items(authors) { author ->
                val isFavorite = favoriteAuthors.contains(author)
                AuthorItem(
                    author = author,
                    onAuthorClick = { onAuthorClick(it) },
                    isFavorite = isFavorite,
                    onFavoriteClick = {
                        if (!isFavorite) {
                            Toast.makeText(
                                context,
                                addedAuthorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        viewModel.toggleFavorite(author)
                    }
                )
            }
        }
    }
}

@Composable
private fun AuthorItem(
    author: String,
    onAuthorClick: (String) -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.padding(dimensionResource(R.dimen.dim_8))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.dim_16),
                    vertical = dimensionResource(R.dimen.dim_8)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = author,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onAuthorClick(author) }
            )
            Icon(
                imageVector = if (isFavorite) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Filled.FavoriteBorder
                },
                contentDescription = if (isFavorite) {
                    stringResource(R.string.remove_favorite)
                } else {
                    stringResource(R.string.add_favorite)
                },
                modifier = Modifier.clickable { onFavoriteClick() }
            )
        }
    }
}

@Composable
private fun getTopBarTitle(userName: String): String {
    return stringResource(R.string.welcome, userName)
}
