package me.hhhaiai.refcore.utils;

public class Runtime {

    private static volatile boolean g64 = false;
    private static volatile boolean isArt = true;

    static {
        try {
            g64 =
                    (boolean)
                            Class.forName("dalvik.system.VMRuntime")
                                    .getDeclaredMethod("is64Bit")
                                    .invoke(
                                            Class.forName("dalvik.system.VMRuntime")
                                                    .getDeclaredMethod("getRuntime")
                                                    .invoke(null));
        } catch (Exception e) {
            g64 = false;
        }
        isArt = System.getProperty("java.vm.version").startsWith("2");
    }

    public static boolean is64Bit() {
        return g64;
    }

    public static boolean isArt() {
        return isArt;
    }
}
