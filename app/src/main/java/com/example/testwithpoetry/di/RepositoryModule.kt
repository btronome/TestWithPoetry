package com.example.testwithpoetry.di

import com.example.testwithpoetry.data.local.dao.FavoriteAuthorDao
import com.example.testwithpoetry.data.local.dao.UserDao
import com.example.testwithpoetry.data.repository.authors.FavoriteAuthorsRepository
import com.example.testwithpoetry.data.repository.authors.implementation.FavoriteAuthorsRepositoryImpl
import com.example.testwithpoetry.data.repository.user.UserRepository
import com.example.testwithpoetry.data.repository.user.implementation.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesUserRepository(dao: UserDao): UserRepository {
        return UserRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun providesFavoriteAuthorsRepository(dao: FavoriteAuthorDao): FavoriteAuthorsRepository {
        return FavoriteAuthorsRepositoryImpl(dao)
    }
}