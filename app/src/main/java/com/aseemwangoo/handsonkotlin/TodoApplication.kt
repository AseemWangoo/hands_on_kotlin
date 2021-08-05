package com.aseemwangoo.handsonkotlin

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import timber.log.Timber

class TodoApplication() : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return if (BuildConfig.DEBUG) {
            Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG).build()
        } else {
            Configuration.Builder().setMinimumLoggingLevel(Log.ERROR).build()
        }
    }
}