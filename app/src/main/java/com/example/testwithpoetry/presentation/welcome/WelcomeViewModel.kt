package com.example.testwithpoetry.presentation.welcome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testwithpoetry.domain.usecase.user.HasUsersUseCase
import com.example.testwithpoetry.domain.usecase.user.RegisterUserUseCase
import com.example.testwithpoetry.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val hasUsersUseCase: HasUsersUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {
    private val _welcomeState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val welcomeState: StateFlow<UiState<Boolean>> = _welcomeState.asStateFlow()

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var birthday by mutableStateOf(0L)

    init {
        hasUsers()
    }

    private fun hasUsers() {
        viewModelScope.launch {
            hasUsersUseCase.invoke().collectLatest { value ->
                _welcomeState.value = UiState.Success(value)
            }
        }
    }

    fun isRegistrationFormValid(): Boolean {
        return name.isNotBlank() &&
                email.contains("@") &&
                birthday != 0L
    }

    fun registerUser(name: String, email: String, birthday: Long) {
        viewModelScope.launch {
            try {
                registerUserUseCase.invoke(name, email, birthday)
                hasUsers()
            } catch (exception: Exception) {
                _welcomeState.value = UiState.Error("Failed to add user")
            }
        }
    }
}