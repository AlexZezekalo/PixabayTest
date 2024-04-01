package com.zezekalo.pixabaytest.domain.common

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

interface CoroutineExecutor {

     val scope: CoroutineScope

     val backgroundDispatcher: CoroutineDispatcher

     val coroutineExceptionHandler: CoroutineExceptionHandler

     fun launchSafe(
         context: CoroutineContext = backgroundDispatcher,
         start: CoroutineStart = CoroutineStart.DEFAULT,
         onCancellation: (e: CancellationException) -> Unit = ::onCancellation,
         onCatch: (t: Throwable) -> Unit = ::onFailed,
         onFinally: (() -> Unit)? = null,
         block: suspend CoroutineScope.() -> Unit,
     ): Job = scope.launch(context + coroutineExceptionHandler, start) {
         try {
             block()
         } catch (e: CancellationException) {
             onCancellation(e)
         } catch (t: Throwable) {
             onCatch(t)
         } finally {
             onFinally?.invoke()
         }
     }

     fun onCancellation(e: CancellationException) { }
     fun onFailed(t: Throwable) { }
}