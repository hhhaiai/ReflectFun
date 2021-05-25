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
        println(info, Log.VERBOSE);
    }

    public static void d(String info) {
        println(info, Log.DEBUG);
    }

    public static void i(String info) {
        println(info, Log.INFO);
    }

    public static void i(String tag, Throwable e) {
        println(tag, Log.getStackTraceString(e), Log.INFO);
    }
    public static void i(String tag, String info) {
        println(tag, info, Log.INFO);
    }

    public static void w(String info) {
        println(info, Log.WARN);
    }


    public static void e(String info) {
        println(info, Log.ERROR);
    }

    public static void wtf(String info) {
        println(info, Log.ASSERT);
    }

    private static void println(String info, int priority) {
        Log.println(priority, TAG, info);
    }

    private static void println(String tag, String info, int priority) {
        Log.println(priority, tag, info);
    }
}
