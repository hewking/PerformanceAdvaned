package com.hewking.advanced.imageloader

import android.graphics.Bitmap
import android.os.Build
import android.text.style.BulletSpan
import android.util.LruCache

/**
 * @program: PerformanceAdvaned
 * @author: hewking
 * @create: 2020-02-22 18:13
 * @description: image load cache
 **/
class MemoryCache {

    companion object{
        private const val TAG = "MemoryCache"
        private const val DEFAULT_MEMROY_CACHE_SIZE = 1024 * 12L
    }

    private val sizePer = 0L
    private val mMemoryCache: LruCache<String,Bitmap>

    init {
        var cacheSize = DEFAULT_MEMROY_CACHE_SIZE
        if (sizePer > 0) {
            cacheSize = sizePer * Runtime.getRuntime().maxMemory().div(1024L)
        }
        mMemoryCache = object : LruCache<String,Bitmap>(cacheSize.toInt()){
            override fun sizeOf(key: String?, value: Bitmap?): Int {
                val bitmapSize = getBitmapSize(value)
                return if (bitmapSize / 1024 == 0) 1 else bitmapSize
            }

            override fun entryRemoved(
                evicted: Boolean,
                key: String?,
                oldValue: Bitmap?,
                newValue: Bitmap?
            ) {
                super.entryRemoved(evicted, key, oldValue, newValue)
            }
        }
    }

    fun getBitmapSize(bitmap: Bitmap?): Int{
        bitmap?:return 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.allocationByteCount
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.byteCount
        }
        return bitmap.rowBytes * bitmap.height
    }

    fun getBitmap(url : String) : Bitmap? {
        return mMemoryCache[url]
    }

    fun addBitmapToCache(url: String ,bitmap: Bitmap) {
        mMemoryCache.put(url,bitmap)
    }

    fun clearCache(){
        mMemoryCache.evictAll()
    }

}