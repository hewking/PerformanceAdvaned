package com.hewking.advanced.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * 异常管理类
 */
@Suppress("DEPRECATION")
class CrashHandler : Thread.UncaughtExceptionHandler {
    private val tag = this.javaClass.simpleName
    /**
     * 系统默认UncaughtExceptionHandler
     */
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    /**
     * context
     */
    private var mContext: Context? = null

    /**
     * 存储异常和参数信息
     */
    private val paramsMap = HashMap<String, String>()

    /**
     * 格式化时间
     */
    @SuppressLint("SimpleDateFormat")
    private val format = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")

    fun init(context: Context) {
        mContext = context
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置该CrashHandler为系统默认的
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * uncaughtException 回调函数
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        handleException(ex)
        mDefaultHandler?.uncaughtException(thread, ex)
    }

    /**
     * 处理该异常
     */
    private fun handleException(ex: Throwable?) {
        if (ex == null) return
        //收集设备参数信息
        collectDeviceInfo(mContext!!)
        //添加自定义信息
        addCustomInfo()
        kotlin.runCatching {
            //使用Toast来显示异常信息
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    mContext,
                    ex.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        //保存日志文件
        saveCrashInfo2File(ex)
    }

    /**
     * 收集设备参数信息
     */
    private fun collectDeviceInfo(ctx: Context) {
        //获取versionName,versionCode
        kotlin.runCatching {
            val pm = ctx.packageManager
            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                paramsMap["versionName"] = versionName
                paramsMap["versionCode"] = versionCode
            }
        }

        //获取所有系统信息
        val fields = Build::class.java.declaredFields
        kotlin.runCatching {
            for (field in fields) {
                field.isAccessible = true
                field.get(null)?.toString()?.let {
                    paramsMap[field.name] = it
                }
            }
        }
    }

    /**
     * 添加自定义参数
     */
    private fun addCustomInfo() {
        Log.i(tag, "addCustomInfo: 程序出错了...")
    }

    /**
     * 保存错误信息到文件中
     */
    private fun saveCrashInfo2File(ex: Throwable) {
        kotlin.runCatching {
            val sb = StringBuilder()
            for ((key, value) in paramsMap) {
                sb.append(key).append("=").append(value).append("\n")
            }

            val writer = StringWriter()
            val printWriter = PrintWriter(writer)
            ex.printStackTrace(printWriter)
            var cause: Throwable? = ex.cause
            while (cause != null) {
                cause.printStackTrace(printWriter)
                cause = cause.cause
            }
            printWriter.close()
            val result = writer.toString()
            sb.append(result)
            val timestamp = System.currentTimeMillis()
            val time = format.format(Date())
            val fileName = "crash-$time-$timestamp.log"
            val path = mContext?.externalCacheDir?.toString() + "/crash/"
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val fos = FileOutputStream(path + fileName)
            fos.write(sb.toString().toByteArray())
            fos.close()
        }
    }

}
