package com.hewking.advanced.plugin

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity

/**
 * 插件Activity 用于给plugin 继承
 */
abstract class BasePluginActivity : AppCompatActivity(), IPluginActivity {

    private var proxyActivity: Activity? = null

    override fun attach(proxyActivity: Activity) {
        this.proxyActivity = proxyActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (proxyActivity == null) {
            super.onCreate(savedInstanceState)
        }
    }

    override fun setContentView(layoutResID: Int) {
        if (proxyActivity != null) {
            proxyActivity?.setContentView(layoutResID)
        } else {
            super.setContentView(layoutResID)
        }
    }

    override fun onStart() {
        if (proxyActivity == null) {
            super.onStart()
        }
    }

    override fun onResume() {
        if (proxyActivity == null) {
            super.onResume()
        }
    }

    override fun onPause() {
        if (proxyActivity == null) {
            super.onPause()
        }
    }

    override fun onStop() {
        if (proxyActivity == null) {
            super.onStop()
        }
    }

    override fun onRestart() {
        if (proxyActivity == null) {
            super.onRestart()
        }
    }

    override fun onDestroy() {
        if (proxyActivity == null) {
            super.onDestroy()
        }
    }

    override fun getResources(): Resources {
        return proxyActivity?.resources?:super.getResources()
    }

    override fun getTheme(): Resources.Theme {
        return proxyActivity?.theme?:super.getTheme()
    }

    override fun getLayoutInflater(): LayoutInflater {
        return if (proxyActivity == null) {
            super.getLayoutInflater()
        } else {
            proxyActivity!!.layoutInflater
        }
    }
}