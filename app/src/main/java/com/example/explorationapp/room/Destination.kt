package com.example.explorationapp.room

import android.location.Location
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "destinations")
data class Destination(
    @PrimaryKey
    @ColumnInfo(name = "id") val destinationId: String,
    var timestamp: Long = 0L,
    var lat: Double = -1.0,
    var lng: Double = -1.0,
    var name: String,
    val imageUrl: String = "",
    val description: String
) {
    override fun toString() = name
}