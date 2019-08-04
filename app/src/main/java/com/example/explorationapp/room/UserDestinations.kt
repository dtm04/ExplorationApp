package com.example.explorationapp.room

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Represents relationship between users and destinations
 */
class UserDestinations{
    @Embedded
    lateinit var destination: Destination

    @Relation(parentColumn = "id", entityColumn = "destination_id")
    var userDestinations: List<User> = arrayListOf()
}