package com.example.explorationapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.explorationapp.room.DestinationRepo
import com.example.explorationapp.room.UserDestinationRepo

class DestinationDetailViewModelFactory(
    private val destinationRepo: DestinationRepo,
    private val userDestinationRepo: UserDestinationRepo,
    private val destnId: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DestinationDetailViewModel(destinationRepo, userDestinationRepo, destnId) as T
    }
}
