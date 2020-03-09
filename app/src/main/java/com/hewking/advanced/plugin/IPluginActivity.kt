package com.hewking.advanced.plugin

import android.app.Activity
import android.os.Bundle

/**
 * 用于定义插件Activity的生命周期
 */
interface IPluginActivity {

    fun attach(proxyActivity: Activity)

    fun onCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onRestart()

    fun onDestroy()

}