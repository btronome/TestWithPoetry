package com.example.testwithpoetry.domain.usecase.author.implementation

import com.example.testwithpoetry.data.common.NetworkResource
import com.example.testwithpoetry.data.repository.poetry.PoetryRepository
import com.example.testwithpoetry.domain.usecase.author.GetAuthorsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAuthorsUseCaseImpl @Inject constructor(
    private val repository: PoetryRepository
) : GetAuthorsUseCase {
    override suspend fun invoke(): Flow<List<String>?> = flow {
        val response = repository.getAuths()
        if (response is NetworkResource.Success) {
            emit(response.data.authors)
        } else {
            emit(null)
        }
    }
}