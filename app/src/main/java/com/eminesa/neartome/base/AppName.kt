package com.eminesa.neartome.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppName : Application() {
    override fun onCreate() {
        super.onCreate()

        // LocaleStorageManager.init(this)

    }
}