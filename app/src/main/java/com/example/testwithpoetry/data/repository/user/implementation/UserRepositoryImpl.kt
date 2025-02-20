package com.example.testwithpoetry.data.repository.user.implementation

import com.example.testwithpoetry.data.local.dao.UserDao
import com.example.testwithpoetry.data.mapper.toDataModel
import com.example.testwithpoetry.data.mapper.toDomainModel
import com.example.testwithpoetry.data.repository.user.UserRepository
import com.example.testwithpoetry.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dao: UserDao) : UserRepository {
    override suspend fun registerUser(user: User) {
        dao.insert(user.toDataModel())
    }

    override fun getAllUsers(): Flow<List<User>> {
        return dao.getAllUsers().map { entities ->
            entities.map {
                it.toDomainModel()
            }
        }
    }
}
