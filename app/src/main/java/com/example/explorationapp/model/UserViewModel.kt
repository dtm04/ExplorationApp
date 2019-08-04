package com.example.explorationapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.explorationapp.room.Destination
import com.example.explorationapp.room.UserDestinationRepo
import com.example.explorationapp.room.UserDestinations

class UserViewModel internal constructor(
    repository: UserDestinationRepo
) : ViewModel() {
    val users = repository.getUsers()
    val userDestinations: LiveData<List<UserDestinations>> =
        repository.getAllDestinations().map { locs ->
            locs.filter { it.userDestinations.isNotEmpty() }
        }
}