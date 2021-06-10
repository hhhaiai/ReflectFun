package me.hhhaiai.refcore.fkhide;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

import dalvik.system.DexFile;
import me.hhhaiai.refcore.utils.MContext;
import me.hhhaiai.refcore.utils.RLog;

public class HideApiImpl {
    public static void hide(Method forName, Method getDeclaredMethod) {

        if (hideImplBySetHiddenApiExemptions(forName, getDeclaredMethod)) {
            return;
        }
        // pass方案
        if (hidImplBypass()) {
            return;
        }

        // 高版本有兼容问题
        if (hideImplByDex(getDeclaredMethod)) {
            return;
        }

        if (hideImplB(forName, getDeclaredMethod)) {
            return;
        }
        if (hideImplC(forName, getDeclaredMethod)) {
            return;
        }
        if (hideImplD(forName, getDeclaredMethod)) {
            return;
        }
    }

    private static boolean hidImplBypass() {
        try {
            PassHideen.addHiddenApiExemptions("L");
            return true;
        } catch (Throwable e) {
        }
        return false;
    }

    //http://androidxref.com/9.0.0_r3/xref/art/test/674-hiddenapi/src-art/Main.java#100
    private static boolean hideImplBySetHiddenApiExemptions(Method forName, Method getDeclaredMethod) {
        try {
            Class<?> vmRuntimeClass = (Class<?>) forName.invoke(null, "dalvik.system.VMRuntime");
            Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null);
            Method setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "setHiddenApiExemptions", new Class[]{String[].class});
            Object sVmRuntime = getRuntime.invoke(null);
            setHiddenApiExemptions.invoke(sVmRuntime, new Object[]{new String[]{"L"}});
            return true;
        } catch (Throwable e) {
            RLog.e(e);
        }
        return false;
    }

    // 兼容方案
    // 系统通过检测classloader是否为null来检测该类是否是系统类，
    // DexFile有个API可以指定这个类的classloader，弄个null上去就能过检测了
    private static boolean hideImplByDex(Method getDeclaredMethod) {
        try {
            String dex = "ZGV4CjAzNQCi6VP1NL4ulKqUHo8KwXD8+Hq2zYqVqDtoBQAAcAAAAHhWNBIAAAAAAAAAALAEAAAaAAAAcAAAAA4AAADYAAAAAwAAABABAAAAAAAAAAAAAAUAAAA0AQAAAQAAAFwBAADsAwAAfAEAAKACAACoAgAAswIAALYCAAC+AgAAwwIAAN8CAADyAgAAFgMAADkDAABbAwAAbwMAAIMDAACyAwAAzgMAANEDAADlAwAA+gMAAA8EAAAoBAAAMQQAAEQEAABQBAAAWAQAAHAEAAB3BAAAAwAAAAUAAAAGAAAABwAAAAgAAAAJAAAACgAAAAsAAAAMAAAADQAAAA4AAAAPAAAAEAAAABEAAAAEAAAABgAAAJACAAAEAAAACQAAAJgCAAAOAAAACgAAAAAAAAAAAAIAAAAAAAAAAgAZAAAAAgABABQAAAAGAAIAAAAAAAkAAAAWAAAAAAAAAAEAAAAGAAAAAAAAAAEAAAB4AgAAoQQAAAAAAAABAAAAkwQAAAEAAQABAAAAfQQAAAQAAABwEAMAAAAOAAkAAAADAAAAggQAAGYAAAASCBIlEhcSBhwAAgAaARQAI1ILABwDBwBNAwIGHAMLAE0DAgduMAIAEAIMAhwAAgAaARMAI3MLABwEBwBNBAMGbjACABADDAAjcQwAGgMSAE0DAQZuMAQAgAEMAB8AAgAjUQwAGgMVAE0DAQZNCAEHbjAEAAIBDAEfAQkAI1MMABoEFwBNBAMGI3QLABwFDQBNBQQGTQQDB24wBAACAwwAHwAJACNiDABuMAQAgQIMASNyDAAjcw0AGgQCAE0EAwZNAwIGbjAEABACDgAAAAAAAAAAAAEAAAAAAAAAAQAAAHwBAAACAAAABgAMAAIAAAAHAAsABjxpbml0PgAJSGlkZS5qYXZhAAFMAAZMSGlkZTsAA0xMTAAaTGRhbHZpay9hbm5vdGF0aW9uL1Rocm93czsAEUxqYXZhL2xhbmcvQ2xhc3M7ACJMamF2YS9sYW5nL0lsbGVnYWxBY2Nlc3NFeGNlcHRpb247ACFMamF2YS9sYW5nL05vU3VjaE1ldGhvZEV4Y2VwdGlvbjsAIExqYXZhL2xhbmcvTnVsbFBvaW50ZXJFeGNlcHRpb247ABJMamF2YS9sYW5nL09iamVjdDsAEkxqYXZhL2xhbmcvU3RyaW5nOwAtTGphdmEvbGFuZy9yZWZsZWN0L0ludm9jYXRpb25UYXJnZXRFeGNlcHRpb247ABpMamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kOwABVgASW0xqYXZhL2xhbmcvQ2xhc3M7ABNbTGphdmEvbGFuZy9PYmplY3Q7ABNbTGphdmEvbGFuZy9TdHJpbmc7ABdkYWx2aWsuc3lzdGVtLlZNUnVudGltZQAHZm9yTmFtZQARZ2V0RGVjbGFyZWRNZXRob2QACmdldFJ1bnRpbWUABmludm9rZQAWc2V0SGlkZGVuQXBpRXhlbXB0aW9ucwAFdmFsdWUABHdvcmsABAAHDgAGAAdKARIPARoP4QEUD2nTAAIBARgcBBgEGAgYAxgFAAACAACBgASEAwEJnAMADwAAAAAAAAABAAAAAAAAAAEAAAAaAAAAcAAAAAIAAAAOAAAA2AAAAAMAAAADAAAAEAEAAAUAAAAFAAAANAEAAAYAAAABAAAAXAEAAAMQAAABAAAAfAEAAAEgAAACAAAAhAEAAAYgAAABAAAAeAIAAAEQAAACAAAAkAIAAAIgAAAaAAAAoAIAAAMgAAACAAAAfQQAAAQgAAABAAAAkwQAAAAgAAABAAAAoQQAAAAQAAABAAAAsAQAAA==";

            byte[] bytes = Base64.decode(dex, Base64.NO_WRAP);
            File f = getCachePath();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bytes);

            DexFile dexFile = new DexFile(f);
            Class<?> hideClass = dexFile.loadClass("Hide", null);
            Method work = (Method) getDeclaredMethod.invoke(hideClass, "work", null);
            work.invoke((Object) null);
            return true;
        } catch (Throwable e) {
        }
        return false;
    }

    private static File getCachePath() {
        Context ctx = MContext.getContext();
        if (ctx != null) {
            return ctx.getCacheDir();
        }
        // 取系统临时目录. 有机器设置是/data/local/tmp ， 有的是当前私有目录
        String tmpDir = System.getProperty("java.io.tmpdir");
        if (TextUtils.isEmpty(tmpDir)) {
            return null;
        }
        File tmp = new File(tmpDir);
        if (!tmp.exists()) {
            return null;
        }
        return tmp;
    }

    //https://www.androidos.net.cn/android/10.0.0_r6/xref/art/test/674-hiddenapi/src-art/Main.java
    private static boolean hideImplB(Method forName, Method getDeclaredMethod) {
        //  private static native void setWhitelistAll(boolean value);

        return false;
    }

    //但是在高版本，谷歌开始不再信任 classloader 为 null 的类 https://android-review.googlesource.com/c/platform/art/+/1664304
    // Don't trust unknown caller when accessing hidden API
    // * Or apps can just call attachCurrentThread() to hide its
    //   caller in JNI and thus bypass hidden API restrictions.
    //   https://github.com/ChickenHook/RestrictionBypass
    private static boolean hideImplC(Method forName, Method getDeclaredMethod) {

        return false;
    }

    // //dexfile 也要被加入隐藏 api 列表中
    //        //https://android-review.googlesource.com/c/platform/libcore/+/1666599
    //        //Mark dalvik.system.DexFile.loadClass as @UnsupportedAppUsage
    //        //
    //        // * Apps can call loadClass() will a NULL ClassLoader as parameter
    //        //   and thus pass hidden API restrictions because we assume it
    //        //   comes from platform.
    private static boolean hideImplD(Method forName, Method getDeclaredMethod) {
        return false;
    }
}
