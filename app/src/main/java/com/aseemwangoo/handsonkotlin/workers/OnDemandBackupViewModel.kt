package com.aseemwangoo.handsonkotlin.workers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.aseemwangoo.handsonkotlin.ONDEMAND_BACKUP_WORK_NAME
import com.aseemwangoo.handsonkotlin.TAG_BACKUP
import com.aseemwangoo.handsonkotlin.TAG_FILE

class OnDemandBackupViewModel(application: Application) : AndroidViewModel(application) {
    private val workManager = WorkManager.getInstance(application)

    // PROGRESS
    internal val backupDataInfo: LiveData<List<WorkInfo>> = workManager
        .getWorkInfosByTagLiveData(TAG_BACKUP)

    internal val saveInFileInfo: LiveData<List<WorkInfo>> = workManager
        .getWorkInfosByTagLiveData(TAG_FILE)

    // END PROGRESS

    /**
     * Create the WorkRequest
     */
    internal fun beginBackup() {
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        var continuation = workManager.beginUniqueWork(
            ONDEMAND_BACKUP_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            OneTimeWorkRequest.from(OnDemandBackupWorker::class.java)
        )

        // BACKUP WORKER
        val backupBuilder = OneTimeWorkRequestBuilder<OnDemandBackupWorker>()
        backupBuilder.addTag(TAG_BACKUP)
        backupBuilder.setConstraints(constraints)
        continuation = continuation.then(backupBuilder.build())

        // SAVE FILE WORKER
        val saveInFile = OneTimeWorkRequest.Builder(FileWorker::class.java)
            .setConstraints(constraints)
            .addTag(TAG_FILE)
            .build()

        continuation = continuation.then(saveInFile)
        continuation.enqueue()
    }

    /**
     * Cancel the WorkRequest
     */
    internal fun cancelBackup() {
        workManager.cancelUniqueWork(ONDEMAND_BACKUP_WORK_NAME)
    }
 }

class OnDemandBackupViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnDemandBackupViewModel::class.java)) {
            return OnDemandBackupViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}