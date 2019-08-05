package com.example.explorationapp.workers


import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.explorationapp.room.AppRoomDatabase
import com.example.explorationapp.room.Destination
import com.example.explorationapp.utils.USER_DATA_FILENAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope

/**
 * Helper class to create and populate database with user locations.
 * Code sourced from and inspired by Google-Sunflower Project.
 */
class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

    override suspend fun doWork(): Result = coroutineScope {
        try {
            Log.i(TAG, "METHOD IS BEING EXECUTED")
            applicationContext.assets.open(USER_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val destType = object : TypeToken<List<Destination>>() {}.type
                    val destList: List<Destination> = Gson().fromJson(jsonReader, destType)
                    val database = AppRoomDatabase.getInstance(applicationContext)
                    database.destinationDao().insertAll(destList)
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
}