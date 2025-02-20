package com.example.testwithpoetry.data.mapper

import com.example.testwithpoetry.data.local.entity.UserEntity
import com.example.testwithpoetry.domain.model.User

fun User.toDataModel(): UserEntity {
    return UserEntity(
        name = this.name,
        email = this.email,
        birthday = this.birthday
    )
}

fun UserEntity.toDomainModel(): User {
    return User(
        name = this.name,
        email = this.email,
        birthday = this.birthday
    )
}
