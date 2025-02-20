package com.example.testwithpoetry.domain.usecase.user.implementation

import com.example.testwithpoetry.domain.usecase.user.GetUsersUseCase
import com.example.testwithpoetry.domain.usecase.user.HasUsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HasUsersUseCaseImpl @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : HasUsersUseCase {
    override suspend fun invoke(): Flow<Boolean> {
        return getUsersUseCase().map { users ->
            users.isNotEmpty()
        }
    }
}