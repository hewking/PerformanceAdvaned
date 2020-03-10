package com.hewking.advanced.plugin

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class StubActivity : BaseStubActivity() {

    companion object {
        fun startStubActivity(
            activity: Activity,
            pluginPath: String,
            activityName: String
        ) {
            Intent(activity, StubActivity::class.java).also {
                it.putExtra(PLUGIN_PATH, pluginPath)
                it.putExtra(PLUGIN_ACTIVITY, activityName)
                activity.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}