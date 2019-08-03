package com.example.explorationapp.room

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT user_name FROM user_table")
    fun getUserName(): String

    //@Insert
    //suspend fun  setUserName(uname: String)

    @Insert
    suspend fun insert(destn: Destination)
}