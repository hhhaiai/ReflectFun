package me.hhhaiai.refcore.utils;

import android.util.Log;

public class RLog {
    private static final String TAG = "hhhaiai.rvoke";
    private static boolean isLog = true;

    public static void v(Throwable igone) {
        v(Log.getStackTraceString(igone));
    }

    public static void v(String tag, Throwable igone) {
        v(tag, Log.getStackTraceString(igone));
    }

    public static void v(String info) {
        v(TAG, info);
    }

    public static void v(String tag, String info) {
        print(Log.VERBOSE, tag, info);
    }

    public static void d(Throwable igone) {
        d(Log.getStackTraceString(igone));
    }

    public static void d(String tag, Throwable igone) {
        d(tag, Log.getStackTraceString(igone));
    }

    public static void d(String info) {
        d(TAG, info);
    }

    public static void d(String tag, String info) {
        print(Log.DEBUG, tag, info);
    }

    public static void i(Throwable igone) {
        i(Log.getStackTraceString(igone));
    }

    public static void i(String tag, Throwable igone) {
        i(tag, Log.getStackTraceString(igone));
    }

    public static void i(String info) {
        i(TAG, info);
    }

    public static void i(String tag, String info) {
        print(Log.INFO, tag, info);
    }

    public static void w(Throwable igone) {
        w(Log.getStackTraceString(igone));
    }

    public static void w(String tag, Throwable igone) {
        w(tag, Log.getStackTraceString(igone));
    }

    public static void w(String info) {
        w(TAG, info);
    }

    public static void w(String tag, String info) {
        print(Log.WARN, tag, info);
    }

    public static void e(Throwable igone) {
        e(Log.getStackTraceString(igone));
    }

    public static void e(String tag, Throwable igone) {
        e(tag, Log.getStackTraceString(igone));
    }

    public static void e(String info) {
        e(TAG, info);
    }

    public static void e(String tag, String info) {
        print(Log.ERROR, tag, info);
    }

    public static void wtf(Throwable igone) {
        wtf(Log.getStackTraceString(igone));
    }

    public static void wtf(String tag, Throwable igone) {
        wtf(tag, Log.getStackTraceString(igone));
    }

    public static void wtf(String info) {
        wtf(TAG, info);
    }

    public static void wtf(String tag, String info) {
        print(Log.ASSERT, tag, info);
    }

    private static void print(int priority, String tag, String info) {
        if (isLog) {
            Log.println(priority, tag, info);
        }
    }
}
