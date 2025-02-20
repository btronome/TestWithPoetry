package com.example.testwithpoetry.domain.usecase.author.implementation

import com.example.testwithpoetry.data.repository.authors.FavoriteAuthorsRepository
import com.example.testwithpoetry.domain.usecase.author.FavoriteAuthorsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteAuthorsUseCaseImpl @Inject constructor(
    private val repository: FavoriteAuthorsRepository
) : FavoriteAuthorsUseCase {
    override suspend fun getFavoriteAuthors(): Flow<List<String>> {
        return repository.getFavoriteAuthors().map { list ->
            list.map { it.author }
        }
    }

    override suspend fun addFavorite(author: String) {
        repository.addFavorite(author)
    }

    override suspend fun removeFavorite(author: String) {
        repository.removeFavorite(author)
    }
}