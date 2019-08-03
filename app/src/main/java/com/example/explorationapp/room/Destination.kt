package com.example.explorationapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destinations")
data class Destination(
    @PrimaryKey var timestamp: Long = 0L,
    var lat: Double = -1.0,
    var lng: Double = -1.0
)