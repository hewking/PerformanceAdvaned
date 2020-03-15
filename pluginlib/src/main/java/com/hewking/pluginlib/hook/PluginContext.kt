package com.hewking.pluginlib.hook

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import com.hewking.pluginlib.util.Reflect

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
            Reflect.on(assetManager).call("addAssetPath",pluginPath)
                resources = Resources(
                assetManager,
                context.resources.displayMetrics,
                context.resources.configuration
            )
        } catch (e: ReflectiveOperationException) {

        }
    }

}