package com.example.testwithpoetry.domain.usecase.author

import kotlinx.coroutines.flow.Flow

interface GetAuthorsUseCase {
    suspend operator fun invoke(): Flow<List<String>?>
}