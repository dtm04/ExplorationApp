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
    fun getDestination(): Destination?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestination(destination: Destination)

    @Query("SELECT * FROM destinations")
    fun getAllDestinations(): LiveData<List<Destination>>
}