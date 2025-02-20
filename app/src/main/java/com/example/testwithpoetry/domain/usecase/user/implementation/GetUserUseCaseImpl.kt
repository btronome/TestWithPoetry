package com.example.testwithpoetry.domain.usecase.user.implementation

import com.example.testwithpoetry.domain.model.User
import com.example.testwithpoetry.domain.usecase.user.GetUserUseCase
import com.example.testwithpoetry.domain.usecase.user.GetUsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserUseCaseImpl @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : GetUserUseCase {
    override suspend fun invoke(): Flow<User> {
        return getUsersUseCase().map { users ->
            users.first()
        }
    }
}