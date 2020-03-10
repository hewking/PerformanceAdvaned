package com.hewking.advanced.monitor.ui

import android.os.Looper

object UIPerfMonitor : LogPrintListener{

    const val TAG = "UIPerfMonitor"

    private var mLogPrinter: LogPrinter = LogPrinter(this)
    private var mCpuSampler: CpuSampler = CpuSampler()
    private val mLogWriteThread = LogWriteThread()

    override fun onPrintStart() {
        mCpuSampler.start()
    }

    override fun onPrintEnd(level: Int) {
        mCpuSampler.stop()
        when (level) {
            UI_PERF_LEVEL_1-> {
                val cpuInfoList = mCpuSampler.mCpuInfoList
                val cpuLog = cpuInfoList.map { it.toString() }.reduce { acc, s -> acc + s }
                mLogWriteThread.savelog(cpuLog)
            }
            else ->{

            }
        }
    }

    fun startMonitor(){
        Looper.getMainLooper().setMessageLogging(mLogPrinter)
    }

    fun stopMonitor(){
        Looper.getMainLooper().setMessageLogging(null)
    }

}