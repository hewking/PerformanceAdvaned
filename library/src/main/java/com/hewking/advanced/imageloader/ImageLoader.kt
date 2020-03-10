package com.hewking.advanced.imageloader

import android.graphics.Bitmap
import android.os.AsyncTask
import android.text.TextUtils
import android.widget.ImageView
import java.lang.ref.WeakReference

/**
 * @program: PerformanceAdvaned
 * @author: hewking
 * @create: 2020-02-22 17:28
 * @description: base of ImageLoader
 **/

abstract class ImageLoader {

    /**
     *  是否暂停加载
     */
    private var mPauseWork = false

    private var lock = Object()

    protected open fun loadImage(url: String, imageView: ImageView,bmConfig: BitmapConfig?) {
        if (TextUtils.isEmpty(url)) {
            return
        }
        val task = BitmapLoadTask(url, imageView,bmConfig)
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    fun setPauseWork(isPause: Boolean) {
        synchronized(lock) {
            mPauseWork = isPause
            lock.notifyAll()
        }
    }

    abstract fun downloadBitmap(url: String,bmConfig: BitmapConfig?): Bitmap?

    private inner class BitmapLoadTask(val url: String, imageView: ImageView,val bmConfig: BitmapConfig?) :
        AsyncTask<Unit, Unit, Bitmap?>() {

        private var mImageViewReference = WeakReference(imageView)

        override fun doInBackground(vararg params: Unit?): Bitmap? {
            if (isCancelled) return null
            synchronized(lock) {
                while (mPauseWork) {
                    lock.wait()
                }
                return downloadBitmap(url,bmConfig)
            }
        }

        override fun onPostExecute(bitmap: Bitmap?) {
            super.onPostExecute(bitmap)
            if (isCancelled) return
            mImageViewReference.get()?.setImageBitmap(bitmap)
        }

        override fun onCancelled() {
            super.onCancelled()
            synchronized(lock){
                lock.notifyAll()
            }
        }

    }

}