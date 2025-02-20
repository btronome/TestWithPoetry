package com.example.testwithpoetry.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testwithpoetry.data.local.dao.FavoriteAuthorDao
import com.example.testwithpoetry.data.local.dao.UserDao
import com.example.testwithpoetry.data.local.entity.FavoriteAuthorEntity
import com.example.testwithpoetry.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, FavoriteAuthorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteAuthorsDao(): FavoriteAuthorDao
}
