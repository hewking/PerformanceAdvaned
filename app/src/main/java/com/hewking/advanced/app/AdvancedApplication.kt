package com.hewking.advanced.app

import android.app.Application
import android.content.Context

class AdvancedApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
    }

}