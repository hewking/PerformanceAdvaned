package com.hewking.advanced.monitor.ui

/**
 * @program: PerformanceAdvaned
 * @author: hewking
 * @create: 2020-02-21 22:22
 * @description: CpuInfo
 **/
data class CpuInfo(val mId: Long
                   ){
    var mCpuRate: Long = 0L
    var mAppRate: Long = 0L
    var mUserRate: Long = 0L
    var mSystemRate: Long = 0L
    var mIoWait: Long = 0L
}