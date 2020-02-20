package com.hewking.advanced.util

const val APPLICATION_START_MONITOR = 1


object TimeMonitorManager {


    private val mMonitorMap = mutableMapOf<Int,TimeMonitor>()


    fun getTimeMonitor(monitorId: Int):TimeMonitor? {
        if (this.mMonitorMap[monitorId] == null) {
            this.mMonitorMap[monitorId] = TimeMonitor(monitorId)
        }
        return this.mMonitorMap[monitorId]
    }

    fun resetTimeMonitor(monitorId: Int){
       this.mMonitorMap.remove(monitorId)
        this.getTimeMonitor(monitorId)
    }

}