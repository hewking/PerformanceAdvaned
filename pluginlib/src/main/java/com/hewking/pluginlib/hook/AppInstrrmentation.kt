package com.hewking.pluginlib.hook

import android.app.Activity
import android.app.Instrumentation
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.hewking.pluginlib.util.Reflect

class AppInstrrmentation(
    private val realContext: Activity,
    private val base: Instrumentation,
    private val pluginContext: PluginContext
) : Instrumentation() {

    companion object {

        private const val KEY_COMPONENT = "commontec_component"

        fun inject(activity: Activity, pluginContext: PluginContext) {
            val activityThread = Reflect.on(activity).get<Any>("mMainThread")
            val base = Reflect.on(activityThread).get<Instrumentation>("mInstrumentation")
            val instrumentation = AppInstrrmentation(activity, base, pluginContext)
            Reflect.on(activityThread).set("mInstrumentation", instrumentation)
            Reflect.on(activity).set("mInstrumentation", instrumentation)
        }
    }

    override fun newActivity(cl: ClassLoader?, className: String?, intent: Intent?): Activity {
        val componentName = intent?.getParcelableExtra<ComponentName>(KEY_COMPONENT)
        val clazz = pluginContext.classLoader.loadClass(componentName?.className)
        intent?.component = componentName
        return clazz?.newInstance() as Activity
    }

    private fun injectActivity(activity: Activity?) {
        val intent = activity?.intent
        val base = activity?.baseContext
        try {
            Reflect.on(base).set("mResources", pluginContext.resources)
            Reflect.on(activity).set("mResources", pluginContext.resources)
            Reflect.on(activity).set("mBase", pluginContext)
            Reflect.on(activity).set("mApplication", pluginContext.application)
            // for native activity
            val componentName = intent!!.getParcelableExtra<ComponentName>(KEY_COMPONENT)
            val wrapperIntent = Intent(intent)
            wrapperIntent.setClassName(componentName.packageName, componentName.className)
            activity.intent = wrapperIntent

        } catch (e: Exception) {
        }
    }

    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?) {
        injectActivity(activity)
        super.callActivityOnCreate(activity, icicle)
    }

    override fun callActivityOnCreate(
        activity: Activity?,
        icicle: Bundle?,
        persistentState: PersistableBundle?
    ) {
        injectActivity(activity)
        super.callActivityOnCreate(activity, icicle, persistentState)
    }

    private fun injectIntent(intent: Intent?) {
        var component: ComponentName? = null
        var oldComponent = intent?.component
        if (component == null || component.packageName == realContext.packageName) {
            component = ComponentName("com.hewking.advanced", "com.hewking.pluginlib.hook.HookStubActivity")
            intent?.component = component
            intent?.putExtra(KEY_COMPONENT, oldComponent)
        }
    }

    fun execStartActivity(
        who: Context,
        contextThread: IBinder,
        token: IBinder,
        target: Activity,
        intent: Intent,
        requestCode: Int
    ): Instrumentation.ActivityResult? {
//        Logger.d("exec...")
        injectIntent(intent)
        return Reflect.on(base)
            .call("execStartActivity", who, contextThread, token, target, intent, requestCode).get()
    }

    fun execStartActivity(
        who: Context?,
        contextThread: IBinder?,
        token: IBinder?,
        target: Activity?,
        intent: Intent,
        requestCode: Int,
        options: Bundle?
    ): Instrumentation.ActivityResult? {
//        Logger.d("exec...")
        injectIntent(intent)
        return Reflect.on(base)
            .call("execStartActivity", who, contextThread, token, target, intent, requestCode, options ?: Bundle())
            .get()
    }

    fun execStartActivity(
        who: Context,
        contextThread: IBinder,
        token: IBinder,
        target: Fragment,
        intent: Intent,
        requestCode: Int,
        options: Bundle?
    ): Instrumentation.ActivityResult? {
//        Logger.d("exec...")
        injectIntent(intent)
        return Reflect.on(base)
            .call("execStartActivity", who, contextThread, token, target, intent, requestCode, options ?: Bundle())
            .get()
    }

    fun execStartActivity(
        who: Context,
        contextThread: IBinder,
        token: IBinder,
        target: String,
        intent: Intent,
        requestCode: Int,
        options: Bundle?
    ): Instrumentation.ActivityResult? {
//        Logger.d("exec...")
        injectIntent(intent)
        return Reflect.on(base)
            .call("execStartActivity", who, contextThread, token, target, intent, requestCode, options ?: Bundle())
            .get()
    }

}