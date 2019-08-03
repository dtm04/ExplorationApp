package com.example.explorationapp.model

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.explorationapp.room.AppRoomDatabase
import com.example.explorationapp.room.Destination
import com.example.explorationapp.room.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    val allFavorites: LiveData<List<Destination>>
    val userName: String

    init {
        val userDao = AppRoomDatabase.getDatabase(application, viewModelScope).userDao()
        val destnDao = AppRoomDatabase.getDatabase(application, viewModelScope).destinationDao()
        repository = UserRepository(userDao, destnDao)
        allFavorites = repository.allFavorites
        userName = repository.userName
    }

    fun insertFav(favorite: Destination) = viewModelScope.launch {
        repository.insertFavorite(favorite)
    }

    /*
    fun setUserName(name: String) = viewModelScope.launch {
        repository.setUserName(name)
    }
    */
}