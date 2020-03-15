package com.example.patchapp

import com.hewking.hotfix.instant_run.PatchDirect

class InstantRunPatchDirect : PatchDirect(){
    override fun invokePatchMethod(method: String?, vararg params: Any?): Any? {
        if (method.equals("getValue")) {
            return getValue()
        }
        return null
    }

    override fun needPatch(methodName: String?): Boolean {
        if (methodName.equals("getValue")) {
            return true
        }
        return false
    }

    fun getValue() = 100

}