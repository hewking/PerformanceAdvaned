package com.hewking.pluginlib.hook

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources

class PluginContext(
    val pluginPath: String,
    val context: Activity,
    val application: Application,
    val classLoader: ClassLoader
) {

    lateinit var resources: Resources

    init {
        try {
            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPathMethod =
                assetManager.javaClass.getDeclaredMethod("addAssetPath", String::class.java)
            addAssetPathMethod.invoke(assetManager, pluginPath)
            resources = Resources(
                assetManager,
                context.resources.displayMetrics,
                context.resources.configuration
            )
        } catch (e: ReflectiveOperationException) {

        }
    }

}