package com.hewking.advanced.monitor.ui

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 采样器，对CPU,堆栈信息，内存使用情况进行采样
 * 开一个线程执行采样操作
 */
abstract class BaseSampler {

    companion object{
        private const val TAG = "BaseSampler"
        private const val INTERVAL_TIME = 500L // 采样间隔500ms
    }

    /**
     * 是否开始采样
     */
    private val mIsSampler = AtomicBoolean(false)

    private var mHandler: Handler? = null
    private var mTH: HandlerThread? = null

    private val mRunnable = object: Runnable{
        override fun run() {
            doSample()
            if (mIsSampler.get()) {
                getControlHandler()?.postDelayed(this, INTERVAL_TIME)
            }
        }
    }

    constructor() {
        Log.d(TAG,"Init Sampler")
    }

    open fun start(){
        if (!mIsSampler.get()) {
            this.getControlHandler()?.removeCallbacks(mRunnable)
            this.getControlHandler()?.post(mRunnable)
            mIsSampler.set(true)
        }
    }

    fun stop(){
        if(this.mIsSampler.get()) {
            this.getControlHandler()?.removeCallbacks(mRunnable)
            mIsSampler.set(false)
        }
    }

    /**
     * 开启异步线程执行
     */
    abstract fun doSample()

    private fun getControlHandler(): Handler?{
        if (mHandler == null) {
            mTH = HandlerThread("UIPerfMonior_Sampler")
            mTH?.start()
            mHandler = Handler(mTH?.looper)
        }
        return mHandler
    }

    fun quitSample(){
        this.mTH?.quit()
    }

}