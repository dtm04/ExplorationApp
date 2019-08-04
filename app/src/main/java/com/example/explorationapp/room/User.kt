package com.example.explorationapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar

/**
 * User database entity.  Using Android Room data storage.
 * See here: https://developer.android.com/training/data-storage/room
 *      Entity: DB Table
 */
@Entity(
    tableName = "user_table",
    foreignKeys = [ForeignKey(entity = Destination::class, parentColumns = ["id"], childColumns = ["destination_id"])],
    indices = [Index("destination_id")])
data class User(
    @ColumnInfo(name = "destination_id") val destId: String,
    @ColumnInfo(name = "destination_timestamp") val timestamp: Calendar = Calendar.getInstance()
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var userId: Long = 0
}