package com.aseemwangoo.handsonkotlin

import android.app.Application
import android.util.Log
import androidx.work.*
import com.aseemwangoo.handsonkotlin.workers.PeriodicBackupWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit

class TodoApplication() : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val periodicBackup = PeriodicWorkRequestBuilder<PeriodicBackupWorker>(15, TimeUnit.MINUTES)
            .addTag(TAG_PERIODIC_BACKUP)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            PERIODIC_BACKUP_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicBackup
        )
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return if (BuildConfig.DEBUG) {
            Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG).build()
        } else {
            Configuration.Builder().setMinimumLoggingLevel(Log.ERROR).build()
        }
    }
}