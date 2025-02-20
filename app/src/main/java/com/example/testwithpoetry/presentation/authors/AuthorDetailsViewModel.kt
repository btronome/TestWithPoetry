package com.example.testwithpoetry.presentation.authors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testwithpoetry.domain.model.Poem
import com.example.testwithpoetry.domain.usecase.author.GetTitlesByAuthorUseCase
import com.example.testwithpoetry.domain.usecase.poem.GetPoemUseCase
import com.example.testwithpoetry.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorDetailsViewModel @Inject constructor(
    private val getTitlesByAuthorUseCase: GetTitlesByAuthorUseCase,
    private val getPoemUseCase: GetPoemUseCase
) : ViewModel() {
    private val _titlesState = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val titlesState: StateFlow<UiState<List<String>>> = _titlesState.asStateFlow()
    private val _poemState = MutableStateFlow<UiState<Poem>?>(null)
    val poemState: StateFlow<UiState<Poem>?> = _poemState.asStateFlow()
    private val _showPoemDialog = MutableStateFlow(false)
    val showPoemDialog: StateFlow<Boolean> = _showPoemDialog.asStateFlow()

    fun fetchTitlesByAuthor(author: String) {
        viewModelScope.launch {
            try {
                getTitlesByAuthorUseCase.invoke(author).collectLatest { titles ->
                    _titlesState.value = UiState.Success(titles)
                }
            } catch (exception: Exception) {
                _titlesState.value = UiState.Error("Failed to fetch titles")
            }
        }
    }

    fun fetchPoem(author: String, title: String) {
        viewModelScope.launch {
            _poemState.value = UiState.Loading
            try {
                getPoemUseCase.invoke(author, title).collectLatest { poem ->
                    poem?.let {
                        _showPoemDialog.value = true
                        _poemState.value = UiState.Success(poem)
                    } ?: run {
                        _poemState.value = UiState.Error("Failed to fetch poem")
                    }
                }
            } catch (exception: Exception) {
                _poemState.value = UiState.Error("Failed to fetch poem")
            }
        }
    }

    fun hidePoemDialog() {
        _showPoemDialog.value = false
    }
}