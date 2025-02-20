package com.example.testwithpoetry.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_authors")
data class FavoriteAuthorEntity(
    @PrimaryKey
    val author: String
)