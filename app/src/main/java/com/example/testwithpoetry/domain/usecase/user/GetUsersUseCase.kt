package com.example.testwithpoetry.domain.usecase.user

import com.example.testwithpoetry.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GetUsersUseCase {
    suspend operator fun invoke(): Flow<List<User>>
}
