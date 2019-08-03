package com.example.explorationapp.room

import android.content.Context
import androidx.room.CoroutinesRoom
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.squareup.okhttp.internal.Internal.instance
import kotlinx.coroutines.CoroutineScope

/**
 * Main database for the whole app.  Tables are listed in the entities field.
 * Provide an abstract "getter" method for each @Dao
 * NOTE: When the schema is modified update the version num and migrate.
 * See here: https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
 */
@Database(entities = [User::class, Destination::class], version = 2)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun destinationDao(): DestinationDao
    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        // make the database a singleton
        fun getDatabase(context: Context, scope: CoroutineScope): AppRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "App_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}