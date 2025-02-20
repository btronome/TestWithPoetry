package com.example.testwithpoetry.domain.usecase.author

import kotlinx.coroutines.flow.Flow

interface FavoriteAuthorsUseCase {
    suspend fun getFavoriteAuthors(): Flow<List<String>>
    suspend fun addFavorite(author: String)
    suspend fun removeFavorite(author: String)
}