package com.example.explorationapp.room

import android.location.Location
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

/**
 * Repository to abstract access to data sources.
 * Provides clean API for data access.
 */
class UserRepository(private val userDao: UserDao, private val destinationDao: DestinationDao) {
    val allFavorites: LiveData<List<Destination>> = destinationDao.getAllDestinations()
    val userName: String = userDao.getUserName()

    // worker threads ensure it is not called from main ui thread
    @WorkerThread
    suspend fun insertFavorite(fav: Destination) {
        destinationDao.insertDestination(fav)
    }

    /*
    @WorkerThread
    suspend fun setUserName(name: String) {
        userDao.setUserName(name)
    }
    */
}