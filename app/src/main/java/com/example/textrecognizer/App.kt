package com.example.textrecognizer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/*
* Created by Annas Surdyanto on 02/03/2023
*/

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}