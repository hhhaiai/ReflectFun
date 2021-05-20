package me.hhhaiai.refcore;

import android.util.Log;

class RLog {
    private static final String TAG = "sanbo";

    public static void e(Throwable igone) {
        Log.e(TAG, Log.getStackTraceString(igone));
    }
    public static void v(String info) {
        Log.v(TAG, info);
    }
    public static void d(String info) {
        Log.d(TAG, info);
    }
    public static void i(String info) {
        Log.i(TAG, info);
    }
    public static void w(String info) {
        Log.w(TAG, info);
    }
    public static void e(String info) {
        Log.e(TAG, info);
    }
    public static void wtf(String info) {
        Log.wtf(TAG, info);
    }
}
