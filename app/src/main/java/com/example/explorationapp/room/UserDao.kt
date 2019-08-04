package com.example.explorationapp.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE id = :userId")
    fun getUser(userId: Long): LiveData<User>

    @Query("SELECT * FROM user_table WHERE destination_id = :destinationId")
    fun getDestination(destinationId: String): LiveData<User?>

    /**
     * This query will tell Room to query both the [Destination] and [User] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM destinations")
    fun getAllDestinations(): LiveData<List<UserDestinations>>

    @Insert
    fun insertUser(user: User): Long

    @Delete
    fun deleteUser(user: User)
}