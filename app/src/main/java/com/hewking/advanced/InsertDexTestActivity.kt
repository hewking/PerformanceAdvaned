package com.hewking.advanced

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.patchapp.ToastMsg
import com.hewking.advanced.util.toast
import kotlinx.android.synthetic.main.activity_hot_fix.*

class InsertDexTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_dex_test)

        btn_toast.setOnClickListener {
            toast(ToastMsg().toastMsg())
        }
    }
}
