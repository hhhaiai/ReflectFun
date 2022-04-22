package me.hhhaiai.refcore.utils;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import me.hhhaiai.refcore.Rvoke;

public class MContext {
    private static Context mContext = null;

    public static Context getContext() {
        try {
            if (mContext == null) {
                Object activityThread = Rvoke.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread");
                Application app = (Application) Rvoke.invokeObjectMethod(activityThread, "getApplication");
                if (app != null) {
                    mContext = app.getApplicationContext();
                }
                if (mContext == null) {
                    app = (Application) Rvoke.invokeStaticMethod("android.app.AppGlobals", "getInitialApplication");
                    if (app != null) {
                        mContext = app.getApplicationContext();
                    }
                }
                if (mContext == null) {
                    // api 15-30(含)包含该接口
                    //public ContextImpl getSystemContext()
                    mContext = (Context) Rvoke.invokeObjectMethod(activityThread, "getSystemContext");
                }
                if (Build.VERSION.SDK_INT > 25) {
                    if (mContext == null) {
                        // api 26-30(含) 包含该接口
                        // public ContextImpl getSystemUiContext() {
                        mContext = (Context) Rvoke.invokeObjectMethod(activityThread, "getSystemUiContext");
                    }
                }
            }
        } catch (Throwable igone) {
        }

        return mContext;
    }
}
