package com.example.testwithpoetry.data.repository.authors.implementation

import com.example.testwithpoetry.data.local.dao.FavoriteAuthorDao
import com.example.testwithpoetry.data.local.entity.FavoriteAuthorEntity
import com.example.testwithpoetry.data.mapper.toDomainModel
import com.example.testwithpoetry.data.repository.authors.FavoriteAuthorsRepository
import com.example.testwithpoetry.domain.model.FavoriteAuthor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteAuthorsRepositoryImpl @Inject constructor(
    private val dao: FavoriteAuthorDao
) : FavoriteAuthorsRepository {
    override fun getFavoriteAuthors(): Flow<List<FavoriteAuthor>> {
        return dao.getFavoriteAuthors().map { entities ->
            entities.map {
                it.toDomainModel()
            }
        }
    }

    override suspend fun addFavorite(author: String) {
        dao.addFavorite(FavoriteAuthorEntity(author = author))
    }

    override suspend fun removeFavorite(author: String) {
        dao.removeFavorite(FavoriteAuthorEntity(author = author))
    }
}
