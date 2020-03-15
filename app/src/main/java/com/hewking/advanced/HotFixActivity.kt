package com.hewking.advanced

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hewking.hotfix.native_hook.NativeHookUtils
import com.hewking.advanced.util.toast
import com.hewking.hotfix.insert_dex.InsertDexUtils
import com.hewking.hotfix.instant_run.InstantRunPatchUtils
import dalvik.system.DexClassLoader

import kotlinx.android.synthetic.main.activity_hot_fix.*
import java.io.File

class HotFixActivity : AppCompatActivity() {

    private var patchClassLoader: DexClassLoader? = null
    private var patchPath: String? = null
    private var dexOutPath: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hot_fix)
        setSupportActionBar(toolbar)
        initPatch()
        initView()
    }

    private fun initView() {
        btn_hotfix.setOnClickListener {
            val fromMethod = HotFixActivity::class.java.getDeclaredMethod("getMsg")
            val patchClass = patchClassLoader?.loadClass("com.hewking.advanced.HotFixActivity")
            val toMethod = patchClass?.getDeclaredMethod("getMsg")
            NativeHookUtils()
                .patch(fromMethod,toMethod)
        }

        btn_insert_dex.setOnClickListener {
            InsertDexUtils.injectDexAtFirst(patchPath,dexOutPath?.absolutePath)
        }

        btn_toast.setOnClickListener {
            toast(getMsg())
        }

        btn_start_fix_act.setOnClickListener {
            Intent(it.context,InsertDexTestActivity::class.java).also {
                startActivity(it)
            }
        }

        btn_instant_run_fix.setOnClickListener {
            InstantRunPatchUtils.inject(patchClassLoader)
        }

        btn_toast_instant_run.setOnClickListener {
            toast(InstantRunPatchUtils().msg)
        }
    }

    private fun initPatch() {
        extractPatch()
        patchPath = File(filesDir,"patchDir").absolutePath
        dexOutPath = File(filesDir,"dexOut")
        if (!dexOutPath!!.exists()) {
            dexOutPath?.mkdir()
        }
        val libPath = File(filesDir,"patchlib").absolutePath
        patchClassLoader = DexClassLoader(patchPath,dexOutPath?.absolutePath
            ,libPath,classLoader)
    }

    private fun extractPatch() {
        val stream = assets.open("patch.apk")
        File(filesDir,"patchDir").writeBytes(stream.readBytes())
    }

    private fun getMsg():String{
        return "我出问题了啊，快来修复我"
    }

}
