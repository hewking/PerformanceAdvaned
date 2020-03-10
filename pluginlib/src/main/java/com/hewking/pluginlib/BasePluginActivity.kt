package com.hewking.pluginlib

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * 插件Activity 用于给plugin 继承
 */
abstract class BasePluginActivity : AppCompatActivity(),
    IPluginActivity {

    companion object{
        private const val TAG = "BasePluginActivity"
    }

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
        proxyActivity?.let {
            it.setContentView(layoutResID)
        } ?: run {
            super.setContentView(layoutResID)
        }
    }

    override fun setContentView(view: View?) {
        proxyActivity?.let {
            it.setContentView(view)
        } ?: run {
            super.setContentView(view)
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

    override fun getResources(): Resources? {
        if (proxyActivity == null) {
            return super.getResources()
        }
        return proxyActivity?.resources
    }

    override fun getTheme(): Resources.Theme? {
        if (proxyActivity == null) {
            return super.getTheme()
        }
        return proxyActivity?.theme
    }

    override fun getLayoutInflater(): LayoutInflater {
        if (proxyActivity == null) {
            return super.getLayoutInflater()
        }
        return proxyActivity?.layoutInflater!!
    }
}