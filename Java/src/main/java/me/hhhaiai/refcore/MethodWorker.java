package me.hhhaiai.refcore;

import java.lang.reflect.Method;

class MethodWorker {

    static Method getMethodImpl(Class<?> clazz, String methodName, Class<?>[] types) {
        try {
            Method method = null;
            try {
                method = clazz.getDeclaredMethod(methodName, types);
            } catch (Throwable e) {
            }
            if (method == null) {
                method = clazz.getMethod(methodName, types);
            }
            if (method != null) {
                method.setAccessible(true);
                return method;
            }
        } catch (Throwable e) {
        }
        return null;
    }

    public static Object invokeMethod(Object obj, Method m, Object[] values) {
        if (m != null) {
            try {
                return m.invoke(obj, values);
            } catch (Throwable e) {
            }
        }
        return null;
    }
}
