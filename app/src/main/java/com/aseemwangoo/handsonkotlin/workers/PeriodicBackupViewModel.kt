package com.aseemwangoo.handsonkotlin.workers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.aseemwangoo.handsonkotlin.PERIODIC_BACKUP_WORK_NAME
import com.aseemwangoo.handsonkotlin.TAG_PERIODIC_BACKUP
import java.util.concurrent.TimeUnit

class PeriodicBackupViewModel(application: Application) : AndroidViewModel(application) {
    private val workManager = WorkManager.getInstance(application)

    /**
     * Create the WorkRequest
     */
    internal fun beginBackup() {
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val periodicBackup = PeriodicWorkRequestBuilder<PeriodicBackupWorker>(1, TimeUnit.DAYS)
            .addTag(TAG_PERIODIC_BACKUP)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            PERIODIC_BACKUP_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicBackup
        )
    }
}

class PeriodicBackupViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeriodicBackupViewModel::class.java)) {
            return PeriodicBackupViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}