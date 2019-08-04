package com.example.explorationapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.explorationapp.room.Destination
import com.example.explorationapp.room.DestinationRepo

class DestinationListViewModel internal constructor(
    destinationRepo: DestinationRepo
): ViewModel() {
    private val NO_LOCATOR: Int = 0
    private val destinationLocator = MutableLiveData<Int>().apply { value = NO_LOCATOR }
    val destinations: LiveData<List<Destination>> = destinationLocator.switchMap {
        if(it == NO_LOCATOR) {
            destinationRepo.getAllDestinations()
        } else {
            destinationRepo.getDestinationsByLocator(it)
        }
    }
}
