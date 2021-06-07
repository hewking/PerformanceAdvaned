package com.hewking.advanced.monitor.ui

import android.util.Log
import android.util.Printer

/**
 *  自定义Printer 用于对Looper.loop 中printer
 *  在dispatchMessage 打印调用，记录前后执行时间
 *  判断是否发生了卡顿
 */
class LogPrinter(listener: LogPrintListener) : Printer {

    companion object{
        private const val TAG = "LogPrinter"
    }

    private var mLogPrintListener: LogPrintListener = listener


    private var mStartTime:Long = 0

    override fun println(x: String?) {
        if (mStartTime <= 0) {
            mStartTime = System.currentTimeMillis()
            this.mLogPrintListener.onPrintStart()
        } else {
            val time = System.currentTimeMillis() - mStartTime
            Log.d(TAG,"log:${x} time:${x}")
            executTime(x,time)
            mStartTime = 0L
        }
    }

    fun executTime(logoInfo: String?, time: Long) {
        val level = when {
            time > TIME_WARNING_LEVEL_2 -> {
                UI_PERF_LEVEL_2
            }
            time > TIME_WARNING_LEVEL_1 -> {
                UI_PERF_LEVEL_1
            }
            else -> {
                UI_PERF_LEVEL_1
            }
        }
        this.mLogPrintListener.onPrintEnd(level)
    }

}