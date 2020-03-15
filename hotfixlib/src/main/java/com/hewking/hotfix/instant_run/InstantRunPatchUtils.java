package com.hewking.hotfix.instant_run;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class InstantRunPatchUtils {
    private static PatchDirect sPatchDirect;

    public int getValue(){
        if (sPatchDirect != null) {
            if (sPatchDirect.needPatch("getValue")) {
                return (int) sPatchDirect.invokePatchMethod("getValue");
            }
        }
        return 200;
    }

    public String getMsg(){
        if (sPatchDirect != null) {
            if (sPatchDirect.needPatch("getMsg")) {
                return (String) sPatchDirect.invokePatchMethod("getMsg");
            }
        }
        return "value is " + getValue();
    }

    public static void inject(ClassLoader classLoader) throws ClassNotFoundException, NoSuchMethodException
            , InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Class<?> patchInfoClass = classLoader.loadClass("com.example.patchapp.PatchInfo");
        patchInfoClass.getMethod("init").invoke(null);
        Map<String,Object> patchDirectMap = (Map<String, Object>) patchInfoClass.getField("sPatchMap").get(null);
        for(String key : patchDirectMap.keySet()) {
            Field field = Class.forName(key).getDeclaredField("sPatchDirect");
            field.setAccessible(true);
            field.set(null,patchDirectMap.get(key));
        }
    }

    public static void uninject(ClassLoader classLoader){
        Class<?> patchInfoClass = null;
        try {
            patchInfoClass = classLoader.loadClass("com.example.patchapp.PatchInfo");
            patchInfoClass.getMethod("init").invoke(null);
            Map<String,Object> patchDirectMap = (Map<String, Object>) patchInfoClass.getField("sPatchMap").get(null);
            for(String key : patchDirectMap.keySet()) {
                Field field = Class.forName(key).getDeclaredField("sPatchDirect");
                field.setAccessible(true);
                field.set(null,null);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
