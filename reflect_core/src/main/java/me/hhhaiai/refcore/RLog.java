package me.hhhaiai.refcore;

import android.util.Log;

class RLog {
    private static final String TAG = "sanbo";

    public static void e(Throwable igone) {
        Log.e(TAG, Log.getStackTraceString(igone));

    }
}
