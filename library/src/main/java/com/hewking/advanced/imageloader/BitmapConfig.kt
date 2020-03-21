package com.hewking.advanced.imageloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream

/**
 * @program: PerformanceAdvaned
 * @author: hewking
 * @create: 2020-02-22 18:17
 * @description: imageload specific quality of bitmap
 **/
class BitmapConfig(
    private val mWidth: Int,
    private val mHeight: Int
) {

    companion object{

        fun calcualateInSampleSize(options: BitmapFactory.Options,
                                   reqWidht: Int,
                                   reqHeight: Int): Int{
            val halfHeight = options.outHeight
            val halfWidth = options.outWidth
            var inSampleSize = 1
            while (halfHeight /inSampleSize > reqHeight
                && halfWidth / inSampleSize > reqWidht) {
                inSampleSize *= 2
            }
            return inSampleSize
        }

    }

    private var mPerfered: Bitmap.Config? = null

    fun getBitmapOptions(stream: InputStream) : BitmapFactory.Options{
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565
        BitmapFactory.decodeStream(stream,null,options)
        options.inSampleSize = calcualateInSampleSize(options,mWidth,mHeight)
        options.inJustDecodeBounds = false
        return options
    }

}