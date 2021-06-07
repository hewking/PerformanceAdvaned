package com.hewking.advanced.monitor.ui

import android.os.Environment.getExternalStorageDirectory

const val TIME_WARNING_LEVEL_2 = 2000L

const val TIME_WARNING_LEVEL_1 = 1000L

const val UI_PERF_LEVEL_1 = 1

const val UI_PERF_LEVEL_2 = 2

var LOG_PATH = getExternalStorageDirectory().absolutePath + "/perfMonitor" + "/log"