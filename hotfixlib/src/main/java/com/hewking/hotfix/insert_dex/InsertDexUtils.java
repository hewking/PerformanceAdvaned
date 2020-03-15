package com.hewking.hotfix.insert_dex;

import com.hewking.pluginlib.util.Reflect;

import java.lang.reflect.Array;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class InsertDexUtils {

    public static void injectDexAtFirst(String dexPath, String defaultDexOutPath) {
        DexClassLoader classLoader = new DexClassLoader(dexPath, defaultDexOutPath, dexPath, getPathClassLoader());
        Object baseDexElements = getElements(getPathList(getPathClassLoader()));
        Object newDexElements = getElements(getPathList(classLoader));
        Object combileElements = combineArray(newDexElements, baseDexElements);
        Reflect.on(getPathList(getPathClassLoader())).set("dexElements", combileElements);
    }

    private static Object getPathList(Object classLoader) {
        return Reflect.on(classLoader).get("pathList");
    }

    private static Object getElements(Object pathList) {
        return Reflect.on(pathList).get("dexElements");
    }

    private static ClassLoader getPathClassLoader() {
        PathClassLoader pathClassLoader = (PathClassLoader) InsertDexUtils.class.getClassLoader();
        return pathClassLoader;
    }

    /**
     * 组合两个数组为一个数组，[firstArray...secondArray]
     *
     * @param firstArray
     * @param secondArray
     */
    public static Object combineArray(Object firstArray, Object secondArray) {
        // 数组getComponentType()才有值返回
        Class<?> componentType = firstArray.getClass().getComponentType();
        int firstLen = Array.getLength(firstArray);
        int combinedLen = firstLen + Array.getLength(secondArray);
        Object combinedArray = Array.newInstance(componentType, combinedLen);
        for (int i = 0; i < combinedLen; i++) {
            if (i < firstLen) {
                Array.set(combinedArray, i, Array.get(firstArray, i));
            } else {
                Array.set(combinedArray, i, Array.get(secondArray, i - firstLen));
            }
        }
        return combinedArray;
    }

}
