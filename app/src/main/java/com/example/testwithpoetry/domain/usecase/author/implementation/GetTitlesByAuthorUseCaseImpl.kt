package com.example.testwithpoetry.domain.usecase.author.implementation

import com.example.testwithpoetry.data.common.NetworkResource
import com.example.testwithpoetry.data.repository.poetry.PoetryRepository
import com.example.testwithpoetry.domain.usecase.author.GetTitlesByAuthorUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTitlesByAuthorUseCaseImpl @Inject constructor(
    private val repository: PoetryRepository
) : GetTitlesByAuthorUseCase {
    override suspend fun invoke(author: String): Flow<List<String>> = flow {
        val response = repository.getTitlesByAuthor(author)
        if (response is NetworkResource.Success) {
            emit(
                response.data.map {
                    it.title
                }.toList()
            )
        } else {
            emit(emptyList())
        }
    }
}
