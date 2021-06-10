package me.hhhaiai.refcore.fkhide;

public class NativeHidden {
    static {
        System.loadLibrary("woo");
    }

    public static native String test();
}
