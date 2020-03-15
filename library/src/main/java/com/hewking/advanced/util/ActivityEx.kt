package com.hewking.advanced.util

import android.app.Activity
import android.widget.Toast

fun Activity.toast(msg:String,dur:Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this,msg,dur).show()
}