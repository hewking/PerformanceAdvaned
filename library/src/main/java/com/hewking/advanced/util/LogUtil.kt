package com.hewking.advanced.util

import android.util.Log

internal enum class Level {
    DEBUG, INFO, WARN, VERBOSE, ERROR
}

object LogUtil {

    private const val TAG = "AdvancedProformance"
    private val enable = true

    fun e(tag: String = TAG, msg: String) {
        if (enable) Log.e(tag, msg)
    }

    fun d(tag: String = TAG, msg: String) {
        if (enable) {
            Log.d(tag, msg)
        }
    }

    fun i(tag: String = TAG, msg: String) {
        if (enable) {
            Log.i(tag, msg)
        }
    }

    fun w(tag: String = TAG, msg: String) {
        if (enable) {
            Log.w(tag, msg)
        }
    }

}