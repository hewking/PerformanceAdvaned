package com.hewking.advanced.monitor.ui

import android.os.Looper

object UIPerfMonitor : LogPrintListener{

    const val TAG = "UIPerfMonitor"

    private var mLogPrinter: LogPrinter

    init {
        mLogPrinter = LogPrinter(this)
    }

    override fun onPrintStart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPrintEnd(level: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun startMonitor(){
        Looper.getMainLooper().setMessageLogging(mLogPrinter)
    }

    fun stopMonitor(){
        Looper.getMainLooper().setMessageLogging(null)
    }

}