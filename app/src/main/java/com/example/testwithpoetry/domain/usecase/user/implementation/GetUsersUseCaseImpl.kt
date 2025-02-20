package com.example.testwithpoetry.domain.usecase.user.implementation

import com.example.testwithpoetry.data.repository.user.UserRepository
import com.example.testwithpoetry.domain.model.User
import com.example.testwithpoetry.domain.usecase.user.GetUsersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCaseImpl @Inject constructor(
    private val repository: UserRepository
) : GetUsersUseCase {
    override suspend fun invoke(): Flow<List<User>> {
        return repository.getAllUsers()
    }
}