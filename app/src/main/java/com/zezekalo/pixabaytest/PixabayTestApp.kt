package com.zezekalo.pixabaytest

import android.app.Application
import com.zezekalo.pixabaytest.logger.AndroidLoggingHandler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PixabayTestApp : Application(){

    override fun onCreate() {
        super.onCreate()

        AndroidLoggingHandler.setup(globalTag = GLOBAL_TAG)
    }

    companion object {

        private const val GLOBAL_TAG = "TAG"
    }
}