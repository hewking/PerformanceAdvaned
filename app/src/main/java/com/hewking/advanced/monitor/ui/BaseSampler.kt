package com.hewking.advanced.monitor.ui

import android.util.Log
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 采样器，对CPU,堆栈信息，内存使用情况进行采样
 * 开一个线程执行采样操作
 */
abstract class BaseSampler {

    companion object{
        private const val TAG = "BaseSampler"
    }

    private val mIsSampler = AtomicBoolean(false)

    constructor() {
        Log.d(TAG,"Init Sampler")
    }

    fun start(){
        if (!mIsSampler.get()) {

            mIsSampler.set(true)
        }
    }

    fun stop(){

    }



}