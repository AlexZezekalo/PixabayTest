package com.zezekalo.pixabaytest.presentation.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debounce(
    private val scope: CoroutineScope,
    private val delay: Long,
    val param: (String) -> Unit
) {

    private var debounceJob: Job? = null

    fun offer(value: String) {
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(delay)
            valueChanged(value)
        }
    }

    private fun valueChanged(value: String) {
        if(value.isNotEmpty())param(value)
    }
}