package com.example.explorationapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DestinationDao {

    // Get the newest destination
    @Query("SELECT * FROM destinations WHERE timestamp = (SELECT MAX(timestamp) FROM destinations)")
    fun getRecentDestination(): Destination?

    @Query("SELECT * FROM destinations WHERE id = :destinationId")
    fun getDestination(destinationId: String): LiveData<Destination>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(destinations: List<Destination>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestination(destination: Destination)

    // TODO: Implement proper lat/lng or other destination/favorite locator
    @Query("SELECT * FROM destinations WHERE timestamp = :loc ORDER BY name")
    fun getDestinationsByLocator(loc: Int): LiveData<List<Destination>>

    @Query("SELECT * FROM destinations ORDER BY name")
    fun getAllDestinations(): LiveData<List<Destination>>
}