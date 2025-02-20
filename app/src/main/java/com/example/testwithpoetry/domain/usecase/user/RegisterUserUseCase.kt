package com.example.testwithpoetry.domain.usecase.user

interface RegisterUserUseCase {
    suspend operator fun invoke(name: String, email: String, birthday: Long)
}
