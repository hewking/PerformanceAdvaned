package com.hewking.advanced.monitor.ui

import android.os.Handler
import android.os.HandlerThread
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.util.*

/**
 * @program: PerformanceAdvaned
 * @author: hewking
 * @create: 2020-02-21 22:57
 * @description: cpu info 信息保存到本地，并且上报到服务器
 **/
class LogWriteThread {

    companion object{
        private const val TAG = "LogWriteThread"
    }

    private var mHander: Handler? = null

    private val lock = Any()

    init {
        initPath()
    }

    fun getControlHander(): Handler?{
        if (mHander == null) {
            val mTH = HandlerThread("LogWriteThread")
            mTH.start()
            mHander = Handler(mTH.looper)
        }
        return mHander
    }

    fun initPath(){
        // TODO()
    }

    fun savelog(cpuLog: String) {
        getControlHander()?.post {
            synchronized(lock) {
                saveLog2Local(cpuLog)
            }
        }
    }

    private fun saveLog2Local(cpulog: String) {
        val file = File(LOG_PATH + "cpulog")
        try {
            val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(file)))
            writer.write(cpulog)
            writer.flush()
            writer.close()
        }catch (ex: Throwable) {

        }finally {

        }
    }

}