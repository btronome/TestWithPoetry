package com.example.testwithpoetry.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testwithpoetry.data.local.entity.FavoriteAuthorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAuthorDao {
    @Query("SELECT * FROM favorite_authors")
    fun getFavoriteAuthors(): Flow<List<FavoriteAuthorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(author: FavoriteAuthorEntity)

    @Delete
    suspend fun removeFavorite(author: FavoriteAuthorEntity)
}
