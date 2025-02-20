package com.example.testwithpoetry.domain.usecase.user.implementation

import com.example.testwithpoetry.data.repository.user.UserRepository
import com.example.testwithpoetry.domain.model.User
import com.example.testwithpoetry.domain.usecase.user.RegisterUserUseCase
import javax.inject.Inject

class RegisterUserUseCaseImpl @Inject constructor(
    private val repository: UserRepository
) : RegisterUserUseCase {
    override suspend fun invoke(name: String, email: String, birthday: Long) {
        repository.registerUser(
            User(
                name = name,
                email = email,
                birthday = birthday
            )
        )
    }
}