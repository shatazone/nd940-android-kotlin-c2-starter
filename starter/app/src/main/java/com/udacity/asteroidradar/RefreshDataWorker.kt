package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import timber.log.Timber
import java.lang.Exception

class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext,
    params
) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        Timber.d("---- Starting Work")
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database, Network.NASA_API)

        return try {
            repository.refreshPictureOfTheDay()
            repository.fetchUpcomingAsteroids()
            Timber.d("---- Work Ended Successfully")
            Result.success()
        }catch (e: Exception) {
            Timber.d("---- Work Failed", e)
            Result.retry()
        }

    }
}