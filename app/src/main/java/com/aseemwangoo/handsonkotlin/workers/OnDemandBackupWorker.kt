package com.aseemwangoo.handsonkotlin.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.aseemwangoo.handsonkotlin.KEY_ONDEMANDWORKER_RESP
import timber.log.Timber

class OnDemandBackupWorker(ctx: Context, workerParams: WorkerParameters) :
    CoroutineWorker(ctx, workerParams) {

    override suspend fun doWork(): Result {
        val appContext = applicationContext
        showNotifications("Backing up the data", appContext)

        return try {
            val res = dummyWork()
            val outputData = workDataOf(KEY_ONDEMANDWORKER_RESP to res)

            Result.success(outputData)
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            Result.failure()
        }
    }

    private suspend fun dummyWork(): String {
        // Faking the network call
        sleep()
        return "Completed successfully!"
    }
}