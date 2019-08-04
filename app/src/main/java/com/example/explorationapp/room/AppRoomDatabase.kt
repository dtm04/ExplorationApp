package com.example.explorationapp.room

import android.content.Context
import androidx.room.CoroutinesRoom
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.squareup.okhttp.internal.Internal.instance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.format.DateTimeFormatter

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
                ).addCallback(AppRoomDatabaseCallback(scope))
                    .allowMainThreadQueries()  // SHOULD NOT BE USED IN PRODUCTION
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppRoomDatabaseCallback(private val scope: CoroutineScope)
            : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase){
                super.onOpen(db)
                INSTANCE?.let {database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.destinationDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(destinationDao: DestinationDao) {
            val timestamp = System.currentTimeMillis()/1000
            val startDestn = Destination(timestamp, 42.36, 71.06)
            destinationDao.insertDestination(startDestn)
        }

    }
}