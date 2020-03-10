package com.hewking.advanced

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hewking.advanced.monitor.time.TimeMonitorManager
import com.hewking.pluginlib.StubActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedOutputStream
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private val MAINACTIVITY_START_MONITOR_ID = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        TimeMonitorManager.getTimeMonitor(MAINACTIVITY_START_MONITOR_ID)?.startMoniter()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_click.setOnClickListener {
            // 模拟ANR 产生场景
            Thread.sleep(30_1000L)
            Log.d(MainActivity::class.java.simpleName, "after sleep")
        }
        TimeMonitorManager.getTimeMonitor(MAINACTIVITY_START_MONITOR_ID)?.end("onCreate")
        extractPlugin()
        btn_plugin.setOnClickListener {
            val pluginPath = File(filesDir.absolutePath, "plugin.apk").absolutePath
            StubActivity.startStubActivity(
                this@MainActivity,
                pluginPath,
                "com.hewking.pluginapp.MainActivity"
            )
        }
    }

    private fun extractPlugin() {
        var inputStream = assets.open("plugin.apk")
        File(filesDir.absolutePath, "plugin.apk").writeBytes(inputStream.readBytes())
    }
}
