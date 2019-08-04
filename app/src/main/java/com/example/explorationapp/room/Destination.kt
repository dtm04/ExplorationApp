package com.example.explorationapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel


@Parcel
@Entity(tableName = "destinations")
data class Destination(
    @PrimaryKey
    @SerializedName("timestamp")
    var timestamp: Long = 0L,

    @SerializedName("lat")
    var lat: Double = -1.0,
    @SerializedName("lng")
    var lng: Double = -1.0

)