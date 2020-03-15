package com.hewking.advanced

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.patchapp.R

class HotFixActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun getMsg():String{
        return "我是nativke hook path 马上就修复了，即时生效"
    }
}
