package com.hewking.pluginlib

import android.app.Activity
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import dalvik.system.DexClassLoader
import java.io.File

/**
 * 占坑Activity 用于插件Activity的代理
 *主要有一下两点要做代理
 * 1. 生命周期的代码
 * 2. 资源的代理，比如resources,theme
 * 3. classLoader 用于加载对应的pluginActivity
 */
abstract class BaseStubActivity : Activity() {

    companion object {
        /**
         * 定义一些常量
         */
        private const val TAG = "BaseStubActivity"
        const val PLUGIN_PATH = "pluginPath"
        const val PLUGIN_ACTIVITY = "pluginActivity"
        const val APK_PLUGIN_LIB = "pluginlib"
        const val APK_DEX_OUT = "dexout"
    }

    /**
     * pluginActivity 的生命周期代理
     */
    private var pluginActivity: IPluginActivity? = null
    private var classLoader: DexClassLoader? = null
    private var pluginPath: String? = null
    private var pluginResources: Resources? = null
    private var pluginTheme: Resources.Theme? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (classLoader == null) {
            pluginPath = intent.getStringExtra(PLUGIN_PATH)
            val plugin = intent.getStringExtra(PLUGIN_ACTIVITY)
            Log.d(TAG, "pluginPath:$pluginPath pluginActivity:$plugin")
            if (TextUtils.isEmpty(pluginPath)
                || TextUtils.isEmpty(plugin)
            ) {
                throw IllegalArgumentException("pluginPath or PluginActivity maybe null")
            }
            val nativeLibDir = File(
                filesDir,
                APK_PLUGIN_LIB
            ).absolutePath
            val dexOutPath = File(
                filesDir,
                APK_DEX_OUT
            ).absolutePath
            classLoader = DexClassLoader(pluginPath, dexOutPath, nativeLibDir, getClassLoader())
            pluginActivity = classLoader!!.loadClass(plugin).newInstance() as IPluginActivity
            pluginActivity!!.attach(this)
            handlerResources()
            pluginActivity!!.onCreate(savedInstanceState)
        }
    }

    /**
     * 初始化资源的加载
     */
    private fun handlerResources() {
        val assetManager = AssetManager::class.java.newInstance()
        val addAssetPathMethod =
            assetManager.javaClass.getDeclaredMethod("addAssetPath", String::class.java)
        addAssetPathMethod.invoke(assetManager, pluginPath)
        pluginResources = Resources(
            assetManager,
            super.getResources().displayMetrics,
            super.getResources().configuration
        )
        pluginTheme = pluginResources?.newTheme()
        pluginTheme?.setTo(super.getTheme())
    }

    override fun getTheme(): Resources.Theme {
        return pluginTheme ?: super.getTheme()
    }

    override fun getResources(): Resources {
        return if (pluginResources == null) {
            super.getResources()
        } else {
            pluginResources!!
        }
    }

    override fun onPause() {
        super.onPause()
        pluginActivity?.onPause()
    }

    override fun onStart() {
        super.onStart()
        pluginActivity?.onStart()
    }

    override fun onResume() {
        super.onResume()
        pluginActivity?.onResume()
    }

    override fun onStop() {
        super.onStop()
        pluginActivity?.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        pluginActivity?.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
        pluginActivity?.onDestroy()
    }


}