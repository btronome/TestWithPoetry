package com.example.testwithpoetry.presentation.authors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testwithpoetry.domain.usecase.author.FavoriteAuthorsUseCase
import com.example.testwithpoetry.domain.usecase.author.GetAuthorsUseCase
import com.example.testwithpoetry.domain.usecase.user.GetUserUseCase
import com.example.testwithpoetry.presentation.navigation.Route
import com.example.testwithpoetry.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorsViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getAuthorsUseCase: GetAuthorsUseCase,
    private val favoriteAuthorsUseCase: FavoriteAuthorsUseCase
) : ViewModel() {
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()
    private val _authorsState = MutableStateFlow<UiState<List<String>?>>(UiState.Loading)
    val authorsState: StateFlow<UiState<List<String>?>> = _authorsState.asStateFlow()
    private val _favoriteAuthors = MutableStateFlow<List<String>>(emptyList())
    val favoriteAuthors: StateFlow<List<String>> = _favoriteAuthors.asStateFlow()
    val items = listOf(Route.Poetry, Route.Account)

    init {
        fetchUser()
    }

    fun fetchAuthors() {
        viewModelScope.launch {
            try {
                getAuthorsUseCase.invoke().collectLatest { authors ->
                    authors?.let {
                        _authorsState.value = UiState.Success(authors)
                        fetchFavoriteAuthors()
                    } ?: run {
                        _authorsState.value = UiState.Error("Failed to fetch authors")
                    }
                }
            } catch (exception: Exception) {
                _authorsState.value = UiState.Error("Failed to fetch authors")
            }
        }
    }

    fun toggleFavorite(author: String) {
        viewModelScope.launch {
            _favoriteAuthors.update { currentFavorites ->
                if (author in currentFavorites) {
                    favoriteAuthorsUseCase.removeFavorite(author)
                    currentFavorites - author
                } else {
                    favoriteAuthorsUseCase.addFavorite(author)
                    currentFavorites + author
                }
            }
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            getUserUseCase.invoke().collectLatest { user ->
                _userName.value = user.name
            }
        }
    }

    private fun fetchFavoriteAuthors() {
        viewModelScope.launch {
            favoriteAuthorsUseCase.getFavoriteAuthors().collectLatest { favoriteAuthors ->
                _favoriteAuthors.value = favoriteAuthors
            }
        }
    }
}