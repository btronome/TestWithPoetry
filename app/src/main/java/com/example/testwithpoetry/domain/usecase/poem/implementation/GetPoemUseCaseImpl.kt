package com.example.testwithpoetry.domain.usecase.poem.implementation

import com.example.testwithpoetry.data.common.NetworkResource
import com.example.testwithpoetry.data.mapper.toDomainModel
import com.example.testwithpoetry.data.repository.poetry.PoetryRepository
import com.example.testwithpoetry.domain.model.Poem
import com.example.testwithpoetry.domain.usecase.poem.GetPoemUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPoemUseCaseImpl @Inject constructor(
    private val repository: PoetryRepository
) : GetPoemUseCase {
    override suspend fun invoke(author: String, title: String): Flow<Poem?> = flow {
        val response = repository.getPoem(author, title)
        if (response is NetworkResource.Success) {
            emit(
                response.data.map {
                    it.toDomainModel()
                }.toList().first()
            )
        } else {
            emit(null)
        }
    }
}
