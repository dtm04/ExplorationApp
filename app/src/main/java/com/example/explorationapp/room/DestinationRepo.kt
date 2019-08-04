package com.example.explorationapp.room

class DestinationRepo private constructor(private val destinationDao: DestinationDao) {

    fun getAllDestinations() = destinationDao.getAllDestinations()

    fun getDestination(destinationId: String) = destinationDao.getDestination(destinationId)

    fun getDestinationsByLocator(loc: Int) = destinationDao.getDestinationsByLocator(loc)

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: DestinationRepo? = null

        fun getInstance(destinationDao: DestinationDao) =
            instance ?: synchronized(this) {
                instance ?: DestinationRepo(destinationDao).also { instance = it }
            }
    }
}
