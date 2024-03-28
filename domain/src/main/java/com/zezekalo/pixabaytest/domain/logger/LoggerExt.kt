package com.zezekalo.pixabaytest.domain.logger

import java.util.logging.Level
import java.util.logging.Logger


private val Any.logger: Logger
    get() = Logger.getLogger(this::class.java.simpleName)

fun Any.logD(message: String) = logger.log(Level.FINE, message)
fun Any.logI(message: String) = logger.log(Level.INFO, message)
fun Any.logW(message: String) = logger.log(Level.WARNING, message)
fun Any.logE(message: String, throwable: Throwable) = logger.log(Level.SEVERE, message, throwable)