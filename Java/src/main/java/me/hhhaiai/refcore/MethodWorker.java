package me.hhhaiai.refcore;

import java.lang.reflect.Method;

class MethodWorker {

    static Method getMethodImpl(Class<?> clazz, String methodName, Class<?>[] types) {
        try {
            Method method = null;
            try {
                // support private method
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
                if (RefUtils.isStatic(m)) {
                    return m.invoke(null, values);
                } else {
                    return m.invoke(obj, values);
                }

            } catch (Throwable e) {
            }
        }
        return null;
    }
}
