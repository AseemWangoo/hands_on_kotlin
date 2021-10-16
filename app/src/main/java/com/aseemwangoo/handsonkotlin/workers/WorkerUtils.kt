@file:JvmName("WorkerUtils")

package com.aseemwangoo.handsonkotlin.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.aseemwangoo.handsonkotlin.BACKUP_FILE_NAME
import com.aseemwangoo.handsonkotlin.CHANNEL_ID
import com.aseemwangoo.handsonkotlin.DELAY_TIME_MILLIS
import com.aseemwangoo.handsonkotlin.NOTIFICATION_ID
import com.aseemwangoo.handsonkotlin.NOTIFICATION_TITLE
import com.aseemwangoo.handsonkotlin.OUTPUT_PATH
import com.aseemwangoo.handsonkotlin.R
import com.aseemwangoo.handsonkotlin.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.aseemwangoo.handsonkotlin.VERBOSE_NOTIFICATION_CHANNEL_NAME
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

fun sleep() {
    try {
        Thread.sleep(DELAY_TIME_MILLIS, 0)
    } catch (e: InterruptedException) {
        Timber.e(e)
    }
}

fun showNotifications(message: String, ctx: Context) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        val notificationManager =
            ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create notification
    val builder = NotificationCompat.Builder(ctx, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show notification
    NotificationManagerCompat.from(ctx).notify(NOTIFICATION_ID, builder.build())
}

@Throws(FileNotFoundException::class)
fun saveToFile(applicationContext: Context, content: String): Uri {
    val outputDir = File(applicationContext.filesDir, OUTPUT_PATH)
    if (!outputDir.exists()) {
        outputDir.mkdirs()
    }
    val outputFile = File(outputDir, BACKUP_FILE_NAME)
    var out: FileOutputStream? = null

    try {
        out = FileOutputStream(outputFile)
        outputFile.appendText(content)
    } finally {
        out?.let {
            try {
                it.close()
            } catch (ignore: IOException) {
            }
        }
    }

    return Uri.fromFile(outputFile)
}
