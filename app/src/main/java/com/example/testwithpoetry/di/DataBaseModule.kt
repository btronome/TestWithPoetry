package com.example.testwithpoetry.di

import android.content.Context
import androidx.room.Room
import com.example.testwithpoetry.data.local.dao.FavoriteAuthorDao
import com.example.testwithpoetry.data.local.dao.UserDao
import com.example.testwithpoetry.data.local.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun providesDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesUserDao(dataBase: AppDataBase): UserDao {
        return dataBase.userDao()
    }

    @Provides
    fun providesFavoriteAuthorsDao(dataBase: AppDataBase): FavoriteAuthorDao {
        return dataBase.favoriteAuthorsDao()
    }
}
