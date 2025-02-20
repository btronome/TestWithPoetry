package com.example.testwithpoetry.data.mapper

import com.example.testwithpoetry.data.local.entity.FavoriteAuthorEntity
import com.example.testwithpoetry.domain.model.FavoriteAuthor

fun FavoriteAuthorEntity.toDomainModel(): FavoriteAuthor {
    return FavoriteAuthor(
        author = this.author
    )
}
