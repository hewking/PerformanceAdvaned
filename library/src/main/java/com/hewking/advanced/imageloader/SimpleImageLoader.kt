package com.hewking.advanced.imageloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.hewking.advanced.util.IOUtil
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

/**
 * @program: PerformanceAdvaned
 * @author: hewking
 * @create: 2020-02-22 17:28
 * @description: implement image loader by myself
 **/
object SimpleImageLoader : ImageLoader(){

    private val memoryCache = MemoryCache()

    override fun loadImage(url: String, imageView: ImageView,bmConfig: BitmapConfig?) {
        val cache = memoryCache.getBitmap(url)
        if (cache!= null) {
            imageView.setImageBitmap(cache)
            return
        }
        super.loadImage(url, imageView,bmConfig)
    }

    override fun downloadBitmap(imageUrl: String,bmConfig: BitmapConfig?): Bitmap? {
        val url = URL(imageUrl)
        var httpUrlConnection: HttpURLConnection? = null
        var stream: InputStream? = null
        try {
            httpUrlConnection = url.openConnection() as HttpURLConnection
            httpUrlConnection.connect()
            stream = httpUrlConnection.inputStream
            val options = bmConfig?.getBitmapOptions(stream)
            stream.close()
            httpUrlConnection.disconnect()
            httpUrlConnection = url.openConnection() as HttpURLConnection
            stream = httpUrlConnection.inputStream
            val bitmap = BitmapFactory.decodeStream(stream,null,options)
            memoryCache.addBitmapToCache(imageUrl,bitmap!!)
            return bitmap
        }catch (ex: Exception) {

        }finally {
            httpUrlConnection?.disconnect()
            IOUtil.closeQuitely(stream)
        }
        return null
    }

}