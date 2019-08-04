package com.example.explorationapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.explorationapp.room.AppRoomDatabase
import com.example.explorationapp.room.Destination

/**
 * TODO: Delete, this class probably isnt necessary!
 */
class DestinationViewModel(application: Application) : AndroidViewModel(application) {
    val allFavorites: LiveData<List<Destination>>

    init {
        val destnDao = AppRoomDatabase.getDatabase(application, viewModelScope).destinationDao()
        allFavorites = destnDao.getAllDestinations()
    }

}