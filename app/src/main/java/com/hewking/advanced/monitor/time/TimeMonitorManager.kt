package com.hewking.advanced.monitor.time

const val APPLICATION_START_MONITOR = 1


object TimeMonitorManager {


    private val mMonitorMap = mutableMapOf<Int, TimeMonitor>()


    fun getTimeMonitor(monitorId: Int): TimeMonitor? {
        if (mMonitorMap[monitorId] == null) {
            mMonitorMap[monitorId] =
                TimeMonitor(monitorId)
        }
        return mMonitorMap[monitorId]
    }

    fun resetTimeMonitor(monitorId: Int){
       mMonitorMap.remove(monitorId)
        getTimeMonitor(
            monitorId
        )
    }

}