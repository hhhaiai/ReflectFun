package me.hhhaiai.refcore;

import java.lang.reflect.Method;

class MethodWorker {

    static Method getMethodImpl(Class<?> clazz, String methodName, Class<?>... types) {
        if (clazz == null || RUtils.isEmpty(methodName)) {
            return null;
        }
        Method method = null;
        while (clazz != Object.class) {
            try {
                if (types != null && types.length > 0) {
                    method = clazz.getDeclaredMethod(methodName, types);
                } else {
                    method = clazz.getDeclaredMethod(methodName);
                }

                if (method != null) {
                    // jdk9 canAccess(Object)
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    return method;
                }
            } catch (Throwable e) {
            }
            clazz = clazz.getSuperclass();
        }
        return method;
    }

    public static Object invokeMethod(Object obj, Method m, Object... values) {
        if (m != null) {
            try {

                // 兼容静态、非静态
                if (values != null && values.length > 0) {
                    return m.invoke(obj, values);
                } else {
                    return m.invoke(obj);
                }
            } catch (Throwable e) {
            }
        }
        return null;
    }
}
