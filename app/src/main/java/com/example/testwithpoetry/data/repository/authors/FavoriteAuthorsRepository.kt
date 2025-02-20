package com.example.testwithpoetry.data.repository.authors

import com.example.testwithpoetry.domain.model.FavoriteAuthor
import kotlinx.coroutines.flow.Flow

interface FavoriteAuthorsRepository {
    fun getFavoriteAuthors(): Flow<List<FavoriteAuthor>>
    suspend fun addFavorite(author: String)
    suspend fun removeFavorite(author: String)
}