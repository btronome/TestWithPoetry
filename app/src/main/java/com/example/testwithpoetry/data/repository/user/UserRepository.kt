package com.example.testwithpoetry.data.repository.user

import com.example.testwithpoetry.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(user: User)
    fun getAllUsers(): Flow<List<User>>
}