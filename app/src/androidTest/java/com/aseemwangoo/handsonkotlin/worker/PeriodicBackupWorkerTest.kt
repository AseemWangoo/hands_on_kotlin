package com.aseemwangoo.handsonkotlin.worker

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.*
import androidx.work.testing.TestWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import com.aseemwangoo.handsonkotlin.workers.PeriodicBackupWorker
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class PeriodicBackupWorkerTest {

    private lateinit var context: Context
    private lateinit var executor: Executor

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        executor = Executors.newSingleThreadExecutor()
    }

    @Test
    fun testPeriodicBackUpWorker() {
        val worker = TestWorkerBuilder<PeriodicBackupWorker>(
            context = context,
            executor = executor
        ).build()

        val result = worker.doWork()
        assertTrue(result is ListenableWorker.Result.Success)
    }

    @Test
    fun testIfPeriodicBackupRunning() {
        WorkManagerTestInitHelper.initializeTestWorkManager(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)
        val workManager = WorkManager.getInstance(context)

        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val request =
            PeriodicWorkRequestBuilder<PeriodicBackupWorker>(repeatInterval = 24, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        workManager.enqueue(request).result.get()

        with(testDriver) {
            this?.setPeriodDelayMet(request.id)
            this?.setAllConstraintsMet(request.id)
        }

        val workInfo = workManager.getWorkInfoById(request.id).get()

        assertEquals(workInfo.state, WorkInfo.State.ENQUEUED)
    }
}