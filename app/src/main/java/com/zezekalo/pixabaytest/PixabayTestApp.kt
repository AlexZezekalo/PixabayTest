package com.zezekalo.pixabaytest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PixabayTestApp : Application(){

    override fun onCreate() {
        super.onCreate()
    }
}