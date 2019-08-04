package com.example.explorationapp.utils

import android.content.Context
import com.example.explorationapp.model.DestinationDetailViewModelFactory
import com.example.explorationapp.model.DestinationListViewModelFactory
import com.example.explorationapp.model.UserViewModelFactory
import com.example.explorationapp.room.AppRoomDatabase
import com.example.explorationapp.room.DestinationRepo
import com.example.explorationapp.room.UserDestinationRepo


/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 * Sourced from Google Sunflower Project to learn about MVM architecture and ROOM storage.
 */
object InjectorUtils {

    private fun getDestinationRepository(context: Context): DestinationRepo {
        return DestinationRepo.getInstance(
            AppRoomDatabase.getInstance(context.applicationContext).destinationDao())
    }

    private fun getUserRepository(context: Context): UserDestinationRepo {
        return UserDestinationRepo.getInstance(
            AppRoomDatabase.getInstance(context.applicationContext).userDao())
    }

    fun provideUserViewModelFactory(
        context: Context
    ): UserViewModelFactory {
        val repository = getUserRepository(context)
        return UserViewModelFactory(repository)
    }

    fun provideDestinationListViewModelFactory(context: Context): DestinationListViewModelFactory {
        val repository = getDestinationRepository(context)
        return DestinationListViewModelFactory(repository)
    }

    fun provideDestinationDetailViewModelFactory(
        context: Context,
        destnId: String
    ): DestinationDetailViewModelFactory {
        return DestinationDetailViewModelFactory(
            getDestinationRepository(context),
            getUserRepository(context), destnId)
    }
}
