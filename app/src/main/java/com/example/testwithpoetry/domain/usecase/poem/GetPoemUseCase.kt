package com.example.testwithpoetry.domain.usecase.poem

import com.example.testwithpoetry.domain.model.Poem
import kotlinx.coroutines.flow.Flow

interface GetPoemUseCase {
    suspend operator fun invoke(author: String, title: String): Flow<Poem?>
}