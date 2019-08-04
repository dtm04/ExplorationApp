package com.example.explorationapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.explorationapp.room.Destination
import com.example.explorationapp.room.DestinationRepo
import com.example.explorationapp.room.UserDestinationRepo
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

// favorite details fragment
class DestinationDetailViewModel(
    destinationRepo: DestinationRepo,
    private val userDestinationRepo: UserDestinationRepo,
    private val destinationId: String
) : ViewModel() {

    val isAdded: LiveData<Boolean>
    val destination: LiveData<Destination>

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {
        val userDestination = userDestinationRepo.getUserByDestination(destinationId)
        isAdded = userDestination.map { it != null }
        destination = destinationRepo.getDestination(destinationId)
    }

    fun addDestination() {
        viewModelScope.launch {
            userDestinationRepo.createUserDestination(destinationId)
        }
    }
}