package com.zezekalo.pixabaytest.domain.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler

interface PresentationDataDelegate {

    val backgroundDispatcher: CoroutineDispatcher

    val coroutineExceptionHandler: CoroutineExceptionHandler
}