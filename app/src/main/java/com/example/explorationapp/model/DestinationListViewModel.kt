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
    private val destinationLocator = MutableLiveData<Int>().apply { value = NO_LOCATOR }

    val destinations: LiveData<List<Destination>> = destinationLocator.switchMap {
        if(it == NO_LOCATOR) {
            destinationRepo.getAllDestinations()
        } else {
            destinationRepo.getDestinationsByLocator(it)
        }
    }

    fun setDestinationLocator(num: Int) {
        destinationLocator.value = num
    }

    fun clearDestinationLocator() {
        destinationLocator.value = NO_LOCATOR
    }

    fun isFiltered() = destinationLocator.value != NO_LOCATOR

    companion object {
        private const val NO_LOCATOR: Int = -1
    }
}
