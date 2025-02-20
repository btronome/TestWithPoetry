package com.example.testwithpoetry.domain.usecase.author

import kotlinx.coroutines.flow.Flow

interface GetTitlesByAuthorUseCase {
    suspend operator fun invoke(author: String): Flow<List<String>>
}