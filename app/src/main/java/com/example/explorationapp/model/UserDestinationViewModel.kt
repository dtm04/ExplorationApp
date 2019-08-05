package com.example.explorationapp.model

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.explorationapp.room.UserDestinations
import java.text.SimpleDateFormat
import java.util.*

class UserDestinationViewModel(destinations: UserDestinations) : ViewModel() {
    private val destination = checkNotNull(destinations.destination)
    private val user = destinations.userDestinations[0]
    private val dateFormat by lazy { SimpleDateFormat("MMM d, yyyy", Locale.US) }

    val imageUrl = ObservableField<String>(destination.imageUrl)
    val name = ObservableField<String>(destination.name)
    val timestamp = ObservableField<String>(dateFormat.format(user.timestamp.time))
}