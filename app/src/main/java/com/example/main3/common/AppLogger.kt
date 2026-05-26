package com.example.main3.common

import android.util.Log

class AppLogger(val t: String) {
    private val baseTag = "AppLoggerMain"

    fun i(action: String, message: String) {
        Log.i(baseTag, "[$t]: $action - $message")
    }

    fun d(action: String, message: String) {
        Log.d(baseTag, "[$t]: $action - $message")
    }

    fun e(action: String, message: String, e: Throwable? = null) {
        Log.e(baseTag, "[$t]: $action - $message", e)
    }
}