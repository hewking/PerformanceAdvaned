package com.hewking.advanced

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hewking.advanced.monitor.time.TimeMonitorManager
import com.hewking.pluginlib.StubActivity
import com.hewking.pluginlib.hook.AppInstrrmentation
import com.hewking.pluginlib.hook.PluginContext
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object{
        const val MAINACTIVITY_START_MONITOR_ID = 2
        const val START_PROXY_PLUGIN = 3
        const val START_HOOT_PLUGIN = 4
    }

    private var pluginClassLoader: ClassLoader? = null
    private var activityName: String? = null

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
        init()
        btn_plugin.setOnClickListener {
            val monitor = TimeMonitorManager.getTimeMonitor(START_PROXY_PLUGIN)
            val pluginPath = File(filesDir.absolutePath, "plugin.apk").absolutePath
            monitor?.startMoniter()
            StubActivity.startStubActivity(
                this@MainActivity,
                pluginPath,
                "com.hewking.pluginapp.MainActivity"
            )
            monitor?.end("Proxy")
        }

        btn_plugin_hook.setOnClickListener {
            val monitor = TimeMonitorManager.getTimeMonitor(START_HOOT_PLUGIN)
            monitor?.startMoniter()
            Intent(this@MainActivity,pluginClassLoader?.loadClass(activityName)).also {
                startActivity(it)
            }
            monitor?.end("Hook")
        }

        btn_start_patch.setOnClickListener {
            Intent(this@MainActivity,HotFixActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun init() {
        val pluginPath = File(filesDir.absolutePath, "plugin.apk").absolutePath
        activityName = "com.hewking.pluginapp.MainActivity"
        val nativeLibDir = File(filesDir, "pluginlib")
        val dexOutPath = File(filesDir, "dexout")
        if (!dexOutPath.exists()) {
            dexOutPath.mkdirs()
        }
        pluginClassLoader = DexClassLoader(pluginPath, dexOutPath.absolutePath, nativeLibDir.absolutePath, this::class.java.classLoader)
//        PluginUtils.classLoader = pluginClassLoader
//        AppInstrrmentation.inject(this, PluginContext(pluginPath, this, application, pluginClassLoader!!))
    }

    private fun extractPlugin() {
        val inputStream = assets.open("plugin.apk")
        File(filesDir.absolutePath, "plugin.apk").writeBytes(inputStream.readBytes())
    }
}
