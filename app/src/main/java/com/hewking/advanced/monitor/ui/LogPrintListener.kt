package com.hewking.advanced.monitor.ui

interface LogPrintListener {

    fun onPrintStart()

    fun onPrintEnd(level: Int)

}