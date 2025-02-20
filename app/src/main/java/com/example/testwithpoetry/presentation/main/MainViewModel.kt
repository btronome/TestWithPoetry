package com.example.testwithpoetry.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testwithpoetry.data.common.NetworkResource
import com.example.testwithpoetry.data.repository.poetry.PoetryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: PoetryRepository
): ViewModel() {
    fun action() {
        viewModelScope.launch {
            val response = repo.getAuths()

            if (response is NetworkResource.Success) {
                response.data.authors.forEach {
                    println(it)
                } 
            }
        }
    }
}
