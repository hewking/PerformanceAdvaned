package com.hewking.advanced.util

import java.io.Closeable
import java.lang.Exception

/**
 * @program: PerformanceAdvaned
 * @author: hewking
 * @create: 2020-02-22 17:52
 * @description: java io util class
 **/
object IOUtil {

    fun closeQuitely(closeable: Closeable?) {
        try {
            closeable?.close()
        }catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

}