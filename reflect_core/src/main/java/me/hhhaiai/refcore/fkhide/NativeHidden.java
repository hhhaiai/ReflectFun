package me.hhhaiai.refcore.fkhide;

import java.io.File;

public class NativeHidden {
    static {
        System.loadLibrary("woo");
    }

    public static native String test();

    public static native int unseal(int targetSdkVersion);

}
