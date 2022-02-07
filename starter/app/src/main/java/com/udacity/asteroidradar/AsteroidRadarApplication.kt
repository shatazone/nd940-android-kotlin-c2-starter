package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        delayedInit()

        Timber.d("App starting")
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
            check(RefreshDataWorker.WORK_NAME)
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS, 15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(RefreshDataWorker.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, repeatingRequest)

        Timber.d("Worker enqueued")
    }

    suspend fun check(workName: String) {
        Timber.d("$workName.check")
        val workManager = WorkManager.getInstance()

        val workInfos = workManager.getWorkInfosForUniqueWork(workName).await()
        if (workInfos.size == 1) {
            // for (workInfo in workInfos) {
            val workInfo = workInfos[0]
            Timber.d("workInfo.state=${workInfo.state}, id=${workInfo.id}")
            if (workInfo.state == WorkInfo.State.BLOCKED || workInfo.state == WorkInfo.State.ENQUEUED || workInfo.state == WorkInfo.State.RUNNING) {
                Timber.d("isAlive")
            } else {
                Timber.d("isDead")

            }
        } else {
            Timber.d("notFound")
        }
    }
}