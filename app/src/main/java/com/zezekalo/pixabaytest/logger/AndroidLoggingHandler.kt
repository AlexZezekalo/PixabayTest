package com.zezekalo.pixabaytest.logger

import android.util.Log
import com.zezekalo.pixabaytest.BuildConfig
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.LogRecord

class AndroidLoggingHandler(
    private val globalTag: String? = null
) : Handler() {

    override fun isLoggable(record: LogRecord?): Boolean {
        return super.isLoggable(record) && BuildConfig.DEBUG
    }

    override fun publish(record: LogRecord) {
        val tag = "$globalTag: ${record.loggerName}"
        val level = getAndroidLevel(record.level)
        val message = record.thrown?.let {
            "${record.message}: ${Log.getStackTraceString(it)}"
        } ?: record.message

        try {
            Log.println(level, tag, message)
        } catch (e: RuntimeException) {
            Log.e(this.javaClass.simpleName, "Error logging message", e)
        }
    }

    private fun getAndroidLevel(level: Level): Int =
        when(level.intValue()) {
            Level.SEVERE.intValue() -> Log.ERROR
            Level.WARNING.intValue() -> Log.WARN
            Level.INFO.intValue() -> Log.INFO
            Level.FINE.intValue() -> Log.DEBUG
            else -> Log.DEBUG
        }

    override fun flush() { }

    override fun close() { }

    companion object {

        fun setup(globalTag: String? = null, level: Level = Level.FINE) {
            val rootLogger = LogManager.getLogManager().getLogger("")

            rootLogger.handlers.onEach { rootLogger.removeHandler(it) }

            rootLogger.addHandler(AndroidLoggingHandler(globalTag))
            rootLogger.level = level
        }
    }
}