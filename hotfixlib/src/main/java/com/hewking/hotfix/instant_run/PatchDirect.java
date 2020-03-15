package com.hewking.hotfix.instant_run;

public abstract class PatchDirect {
    public abstract Object invokePatchMethod(String method,Object... params);
    public abstract boolean needPatch(String methodName);
}
