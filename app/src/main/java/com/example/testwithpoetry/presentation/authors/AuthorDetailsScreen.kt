package com.example.testwithpoetry.presentation.authors

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testwithpoetry.R
import com.example.testwithpoetry.domain.model.Poem
import com.example.testwithpoetry.presentation.components.LoadingIndicator
import com.example.testwithpoetry.presentation.state.UiState
import com.example.testwithpoetry.utils.ShowToast

@Composable
fun AuthorDetailsScreen(author: String, viewModel: AuthorDetailsViewModel = hiltViewModel()) {
    val titlesState by viewModel.titlesState.collectAsState()
    val poemState by viewModel.poemState.collectAsState()
    val showPoemDialog by viewModel.showPoemDialog.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchTitlesByAuthor(author)
    }

    HandleTitlesState(author, titlesState, viewModel)
    HandlePoemState(poemState, showPoemDialog) {
        viewModel.hidePoemDialog()
    }
}

@Composable
private fun HandleTitlesState(
    author: String,
    state: UiState<List<String>>,
    viewModel: AuthorDetailsViewModel
) {
    when (state) {
        is UiState.Loading -> {
            LoadingIndicator()
        }

        is UiState.Success -> {
            LazyColumn {
                items(state.data) { title ->
                    TitleItem(
                        title = title,
                        onTitleClick = {
                            viewModel.fetchPoem(author, title)
                        }
                    )
                }
            }
        }

        is UiState.Error -> {
            ShowToast(message = state.message)
        }
    }
}

@Composable
private fun HandlePoemState(state: UiState<Poem>?, showDialog: Boolean, onDismiss: () -> Unit) {
    state?.let {
        when (state) {
            is UiState.Loading -> {
                LoadingIndicator()
            }

            is UiState.Success -> {
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = onDismiss,
                        title = { Text(text = state.data.title) },
                        text = {
                            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                Text(text = state.data.lines.joinToString(separator = "\n"))
                            }
                        },
                        confirmButton = {
                            Button(onClick = onDismiss) {
                                Text(text = stringResource(R.string.close))
                            }
                        }
                    )
                }
            }

            is UiState.Error -> {
                ShowToast(message = state.message)
            }
        }
    }
}

@Composable
private fun TitleItem(title: String, onTitleClick: (String) -> Unit) {
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
                )
                .clickable { onTitleClick(title) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title)
        }
    }
}