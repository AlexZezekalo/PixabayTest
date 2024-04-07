package com.zezekalo.pixabaytest.presentation.util

import androidx.annotation.CheckResult
import com.zezekalo.pixabaytest.domain.logger.logE
import com.zezekalo.pixabaytest.presentation.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler

@CheckResult
fun getSafeCoroutineExceptionHandler(): CoroutineExceptionHandler =
    CoroutineExceptionHandler { _, throwable ->
        if (BuildConfig.DEBUG) {
            Thread.currentThread()
                .uncaughtExceptionHandler
                ?.uncaughtException(Thread.currentThread(), throwable)
        } else {
            throwable.logE("Unexpected error", throwable)
        }
    }