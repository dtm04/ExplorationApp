package com.example.explorationapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.explorationapp.room.UserDestinationRepo

class UserViewModelFactory(
    private val destinationRepo: UserDestinationRepo
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(destinationRepo) as T
    }
}