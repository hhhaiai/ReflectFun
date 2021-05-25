package me.hhhaiai.refcore;

import android.util.Log;

public class RLog {
    private static final String TAG = "sanbo";

    public static void v(Throwable igone) {
        v(Log.getStackTraceString(igone));
    }

    public static void d(Throwable igone) {
        d(Log.getStackTraceString(igone));
    }

    public static void i(Throwable igone) {
        i(Log.getStackTraceString(igone));
    }

    public static void e(Throwable igone) {
        e(Log.getStackTraceString(igone));
    }

    public static void w(Throwable igone) {
        w(Log.getStackTraceString(igone));
    }

    public static void v(String info) {
        Log.println(Log.VERBOSE, TAG, info);
    }

    public static void d(String info) {
        Log.println(Log.DEBUG, TAG, info);
    }

    public static void i(String info) {
        Log.println(Log.INFO, TAG, info);
    }

    public static void w(String info) {
        Log.println(Log.WARN, TAG, info);
    }

    public static void e(String info) {
        Log.println(Log.ERROR, TAG, info);
    }

    public static void wtf(String info) {
        Log.println(Log.ASSERT, TAG, info);
    }
}
