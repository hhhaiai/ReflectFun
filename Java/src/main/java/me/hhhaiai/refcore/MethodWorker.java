package me.hhhaiai.refcore;

import java.lang.reflect.Method;

class MethodWorker {

    static Method getMethodImpl(Class<?> clazz, String methodName, Class<?>... types) {
        try {
            Method method = null;
            try {
                // support private method
                if (types != null && types.length > 0) {
                    method = clazz.getDeclaredMethod(methodName, types);
                } else {
                    method = clazz.getDeclaredMethod(methodName);
                }

            } catch (Throwable e) {
            }
            if (method == null) {
                if (types != null && types.length > 0) {
                    method = clazz.getMethod(methodName, types);
                } else {
                    method = clazz.getMethod(methodName);
                }
            }
            if (method != null) {
                method.setAccessible(true);
                return method;
            }
        } catch (Throwable e) {
        }
        return null;
    }

    public static Object invokeMethod(Object obj, Method m, Object... values) {
        if (m != null) {
            try {
                if (RefUtils.isStatic(m)) {
                    if (values != null && values.length > 0) {
                        return m.invoke(null, values);
                    } else {
                        return m.invoke(null);
                    }
                } else {
                    if (values != null && values.length > 0) {
                        return m.invoke(obj, values);
                    } else {
                        return m.invoke(obj);
                    }
                }

            } catch (Throwable e) {
            }
        }
        return null;
    }
}
