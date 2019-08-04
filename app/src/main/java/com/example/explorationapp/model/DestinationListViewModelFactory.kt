package com.example.explorationapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.explorationapp.room.DestinationRepo

class DestinationListViewModelFactory(
    private val repository: DestinationRepo
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = DestinationListViewModel(repository) as T
}