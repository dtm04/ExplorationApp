package com.example.explorationapp.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.android.gms.common.util.Strings
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * Repository to abstract access to data sources.
 * Provides clean API for data access.
 */
class UserDestinationRepo(
    private val userDestinationDao: UserDao
) {

    suspend fun createUserDestination(destinationId: String) {
        withContext(IO) {
            val userDestination = User(destinationId)
            userDestinationDao.insertUser(userDestination)
        }
    }

    suspend fun removeUserDestination(user: User) {
        withContext(IO) {
            userDestinationDao.deleteUser(user)
        }
    }

    fun getUserByDestination(destinationId: String) = userDestinationDao.getDestination(destinationId)
    fun getUsers() = userDestinationDao.getAllUsers()
    fun getAllDestinations() = userDestinationDao.getAllDestinations()

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: UserDestinationRepo? = null

        fun getInstance(userDao: UserDao) =
            instance ?: synchronized(this) {
                instance ?: UserDestinationRepo(userDao).also { instance = it }
            }
    }
}