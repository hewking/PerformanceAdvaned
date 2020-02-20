package com.hewking.advanced.util

import android.util.Log

class TimeMonitor(private val monitorId: Int){

    companion object {
        private const val TAG = "TimeMonitor"
    }

    private var mStartTime = 0L

    private val mTimeTags = mutableMapOf<String,Long>()

    fun startMoniter(){
        this.mTimeTags.clear()
        this.mStartTime = System.currentTimeMillis()
    }

    fun recordTimeTag(tag:String){
        if (mTimeTags[tag] != null) {
            mTimeTags.remove(tag)
        }
        val time = System.currentTimeMillis() - this.mStartTime
        this.mTimeTags[tag] = time
    }

    fun end(tag:String,writeLog: Boolean = false){
        this.recordTimeTag(tag)
        this.end(writeLog)
    }

    fun end(writeLog: Boolean = false) {
        if (writeLog) {
            // TODO : write log to file
        }
        this.showTestData()
    }

    fun showTestData(){
        if (this.mTimeTags.isEmpty()) {
            return
        }
        this.mTimeTags.forEach{entry ->
            Log.d(TAG,"${entry.key}: ${entry.value} ms")
        }
    }

}