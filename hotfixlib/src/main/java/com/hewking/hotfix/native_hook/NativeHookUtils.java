package com.hewking.hotfix.native_hook;

import java.lang.reflect.Method;

public class NativeHookUtils {

    static {
        System.loadLibrary("native-hook");
    }

    public native void patch(Method fromMethod,Method toMethod);

    public String getMsg(){
        return "native hotfix";
    }

}
