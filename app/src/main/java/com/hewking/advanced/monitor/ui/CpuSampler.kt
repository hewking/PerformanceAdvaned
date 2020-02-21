package com.hewking.advanced.monitor.ui

import android.util.Log
import java.io.*
import java.lang.Exception


/**
 * CPU 采样器
 */
class CpuSampler : BaseSampler(){

    companion object{
        private const val TAG = "CpuSampler"
    }

    private val mCpuInfoList = mutableListOf<CpuInfo>()

    private var mPid = -1
    private var mUserPre = 0L
    private var mSystemPre = 0L
    private var mIdlePre = 0L
    private var mIoWaitPre = 0L
    private var mTotalPre = 0L
    private var mAppCupTimePre = 0L

    override fun doSample() {
        var cpuReader: BufferedReader? = null
        var pidReader: BufferedReader? = null

        try {
            cpuReader = BufferedReader(InputStreamReader(FileInputStream("/proc/stat")),1024)
            val cpuRate = cpuReader.readLine()?:""
            if (mPid < 0) {
                mPid = android.os.Process.myPid()
            }
            pidReader = BufferedReader(InputStreamReader(FileInputStream("/proc/${mPid}/stat")),1024)
            val pidCpuRate = pidReader.readLine()?:""
            parseCpuRate(cpuRate,pidCpuRate = pidCpuRate)
        }catch (e: Throwable) {
            Log.d(TAG, "doSample:$e")
        }finally {
            try {
                cpuReader?.close()
                pidReader?.close()
            }catch (e: IOException) {
                Log.e(TAG,"doSample:$e")
            }
        }
    }

    private fun parseCpuRate(cpuRate:String,pidCpuRate:String) {
        val cpuInfoArray = cpuRate.split("")
        if (cpuInfoArray.size < 9) return
        val userTime = cpuInfoArray[2].toLong()
        val niceTime = cpuInfoArray[3].toLong()
        val systemTIme = cpuInfoArray[4].toLong()
        val idleTime = cpuInfoArray[5].toLong()
        val ioWaitTime = cpuInfoArray[6].toLong()
        val totalTime = userTime + niceTime + systemTIme + idleTime + ioWaitTime
                        + cpuInfoArray[7].toLong()
                        + cpuInfoArray[8].toLong()

        val pidCpuInfoArray = pidCpuRate.split("")
        if (pidCpuInfoArray.size < 17) return

        val appCpuTime = pidCpuInfoArray[13].toLong()
        + pidCpuInfoArray[14].toLong()
        + pidCpuInfoArray[15].toLong()
        + pidCpuInfoArray[16].toLong()

        if (mAppCupTimePre > 0) {
            val mCi = CpuInfo(System.currentTimeMillis())
            mCi.mCpuRate = (totalTime - idleTime) * 100 / totalTime
            mCi.mAppRate = (appCpuTime - mAppCupTimePre) * 100 / totalTime
            mCi.mSystemRate = (systemTIme - mSystemPre) * 100 / totalTime
            mCi.mUserRate = (userTime - mUserPre) * 100 / totalTime
            mCi.mIoWait = (ioWaitTime - mIoWaitPre) * 100 / totalTime

            synchronized(mCpuInfoList) {
                mCpuInfoList.add(mCi)
                Log.d(TAG,"cpu info:$mCi")
            }
            mUserPre = userTime
            mSystemPre = systemTIme
            mIdlePre = idleTime
            mIoWaitPre = ioWaitTime
            mTotalPre = totalTime
            mAppCupTimePre = appCpuTime
        }
    }

    override fun start() {
        super.start()
    }


}