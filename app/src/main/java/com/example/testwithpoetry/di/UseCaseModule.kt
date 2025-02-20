package com.example.testwithpoetry.di

import com.example.testwithpoetry.data.repository.authors.FavoriteAuthorsRepository
import com.example.testwithpoetry.data.repository.poetry.PoetryRepository
import com.example.testwithpoetry.data.repository.user.UserRepository
import com.example.testwithpoetry.domain.usecase.author.FavoriteAuthorsUseCase
import com.example.testwithpoetry.domain.usecase.author.GetAuthorsUseCase
import com.example.testwithpoetry.domain.usecase.author.GetTitlesByAuthorUseCase
import com.example.testwithpoetry.domain.usecase.author.implementation.FavoriteAuthorsUseCaseImpl
import com.example.testwithpoetry.domain.usecase.author.implementation.GetAuthorsUseCaseImpl
import com.example.testwithpoetry.domain.usecase.author.implementation.GetTitlesByAuthorUseCaseImpl
import com.example.testwithpoetry.domain.usecase.poem.GetPoemUseCase
import com.example.testwithpoetry.domain.usecase.poem.implementation.GetPoemUseCaseImpl
import com.example.testwithpoetry.domain.usecase.user.GetUserUseCase
import com.example.testwithpoetry.domain.usecase.user.GetUsersUseCase
import com.example.testwithpoetry.domain.usecase.user.HasUsersUseCase
import com.example.testwithpoetry.domain.usecase.user.RegisterUserUseCase
import com.example.testwithpoetry.domain.usecase.user.implementation.GetUserUseCaseImpl
import com.example.testwithpoetry.domain.usecase.user.implementation.GetUsersUseCaseImpl
import com.example.testwithpoetry.domain.usecase.user.implementation.HasUsersUseCaseImpl
import com.example.testwithpoetry.domain.usecase.user.implementation.RegisterUserUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun providesGetUsersUseCase(repository: UserRepository): GetUsersUseCase {
        return GetUsersUseCaseImpl(repository)
    }

    @Provides
    fun providesHasUsersUseCase(useCase: GetUsersUseCase): HasUsersUseCase {
        return HasUsersUseCaseImpl(useCase)
    }

    @Provides
    fun providesRegisterUserUseCase(repository: UserRepository): RegisterUserUseCase {
        return RegisterUserUseCaseImpl(repository)
    }

    @Provides
    fun providesGetUserUseCase(useCase: GetUsersUseCase): GetUserUseCase {
        return GetUserUseCaseImpl(useCase)
    }

    @Provides
    fun providesGetAuthorsUseCase(repository: PoetryRepository): GetAuthorsUseCase {
        return GetAuthorsUseCaseImpl(repository)
    }

    @Provides
    fun providesFavoriteAuthorsUseCase(repository: FavoriteAuthorsRepository): FavoriteAuthorsUseCase {
        return FavoriteAuthorsUseCaseImpl(repository)
    }

    @Provides
    fun providesGetTitlesByAuthorUseCase(repository: PoetryRepository): GetTitlesByAuthorUseCase {
        return GetTitlesByAuthorUseCaseImpl(repository)
    }

    @Provides
    fun providesGetPoemUseCase(repository: PoetryRepository): GetPoemUseCase {
        return GetPoemUseCaseImpl(repository)
    }
}