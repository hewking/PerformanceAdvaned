package com.hewking.pluginapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hewking.pluginlib.BasePluginActivity

class MainActivity : BasePluginActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
