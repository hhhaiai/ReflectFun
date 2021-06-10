import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Hide {
    public static void work() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NullPointerException {
        Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
        Class<?> vmRuntimeClass = (Class<?>) Class.class.getDeclaredMethod("forName", String.class).invoke(null, "dalvik.system.VMRuntime");
        Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null);
        Method setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "setHiddenApiExemptions", new Class[]{String[].class});
        Object sVmRuntime = getRuntime.invoke((Object) null);
        setHiddenApiExemptions.invoke(sVmRuntime, new Object[]{new String[]{"L"}});

    }
}
