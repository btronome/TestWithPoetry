package com.example.testwithpoetry.domain.usecase.user

import kotlinx.coroutines.flow.Flow

interface HasUsersUseCase {
    suspend operator fun invoke(): Flow<Boolean>
}