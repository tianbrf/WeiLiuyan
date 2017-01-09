package com.zjf.weike.util;

/**
 * @author :ZJF
 * @version : 2016-12-20 下午 4:10
 */

public class NativeUtil {

    public native static String getKey();

    static {
        System.loadLibrary("native-lib");
    }
}
