package com.example.patchapp;

import com.hewking.hotfix.instant_run.PatchDirect;

import java.util.HashMap;

public class PatchInfo {

    public static HashMap<String, PatchDirect> sPatchMap = new HashMap<>();

    public static void init(){
        sPatchMap.put("com.hewking.hotfix.instant_run.InstantRunPatchUtils",new InstantRunPatchDirect());
    }
}
