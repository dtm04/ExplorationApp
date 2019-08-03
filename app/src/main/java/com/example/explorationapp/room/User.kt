package com.example.explorationapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.ForeignKey

/**
 * User database entity.  Using Android Room data storage.
 * See here: https://developer.android.com/training/data-storage/room
 *      Entity: DB Table
 */
@Entity(tableName = "user_table", foreignKeys = arrayOf(
    ForeignKey(entity = Destination::class, parentColumns = arrayOf("timestamp"), childColumns = arrayOf("destn_timestamp"))
))
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    //ForeignKey timestamp --> location table
    //ForeignKey --> Favorites table?
    //ForeignKey --> Records table? (audio/video/etc)
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "destn_timestamp") var timestamp: Long? = null

)