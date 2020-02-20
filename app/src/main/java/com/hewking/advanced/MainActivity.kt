package com.hewking.advanced

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hewking.advanced.util.TimeMonitorManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val MAINACTIVITY_START_MONITOR_ID = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        TimeMonitorManager.getTimeMonitor(MAINACTIVITY_START_MONITOR_ID)?.startMoniter()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_click.setOnClickListener {
            // 模拟ANR 产生场景
            Thread.sleep(30_1000L)
            Log.d(MainActivity::class.java.simpleName,"after sleep")
        }
        TimeMonitorManager.getTimeMonitor(MAINACTIVITY_START_MONITOR_ID)?.end("onCreate")
    }
}
