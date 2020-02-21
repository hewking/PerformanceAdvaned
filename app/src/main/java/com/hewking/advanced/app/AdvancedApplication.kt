package com.hewking.advanced.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.hewking.advanced.monitor.time.APPLICATION_START_MONITOR
import com.hewking.advanced.monitor.time.TimeMonitorManager

class AdvancedApplication : Application() {

    companion object{

        private val TAG = AdvancedApplication::class.java.simpleName

        private lateinit var mContext: Context

        fun getContext(): Context {
            return mContext
        }
    }


    override fun attachBaseContext(base: Context?) {
        TimeMonitorManager.getTimeMonitor(
            APPLICATION_START_MONITOR
        )?.startMoniter()
        super.attachBaseContext(base)
        mContext = base?:return
        TimeMonitorManager.getTimeMonitor(
            APPLICATION_START_MONITOR
        )?.recordTimeTag("attachBaseContext")
    }

    override fun onCreate() {
        super.onCreate()

        TimeMonitorManager.getTimeMonitor(
            APPLICATION_START_MONITOR
        )?.end("onCreate")
    }

    /**
     * 在系统配置发生改变时调用，比如横竖屏切换
     * 语言切换等
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    /**
     * 在应用低内存是调用
     */
    override fun onLowMemory() {
        super.onLowMemory()
    }

    /**
     * 在应用释放内存时调用
     * @param level 级别
     */
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.i(TAG,"level: $level")
    }

}