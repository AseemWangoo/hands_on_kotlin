@file:Suppress("TooGenericExceptionCaught")

package com.aseemwangoo.handsonkotlin.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.aseemwangoo.handsonkotlin.KEY_PERIODICWORKER_RESP
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PeriodicBackupWorker(ctx: Context, workerParams: WorkerParameters) :
    Worker(ctx, workerParams) {

    private val dateFormatter = SimpleDateFormat(
        "yyyy.MM.dd 'at' HH:mm:ss z", Locale.getDefault()
    )

    override fun doWork(): Result {
        val appContext = applicationContext
        showNotifications("Periodic backing", appContext)

        return try {
            val content = "Periodic back up on ${dateFormatter.format(Date())}.\n"
            val outputUri = saveToFile(appContext, content)

            val data = workDataOf(KEY_PERIODICWORKER_RESP to outputUri.toString())

            Result.success(data)
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            Result.failure()
        }
    }
}
