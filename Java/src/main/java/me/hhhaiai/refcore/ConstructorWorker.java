package me.hhhaiai.refcore;

import java.lang.reflect.Constructor;

class ConstructorWorker {
    static Object newInstance(Class clazz, Class[] types, Object[] values) {

        try {
            Constructor ctor = null;
            try {
                ctor = clazz.getDeclaredConstructor(types);
            } catch (Throwable igone) {
                // if null, has other plan
            }
            if (ctor == null) {
                ctor = clazz.getConstructor(types);
            }
            if (ctor != null) {
                ctor.setAccessible(true);
                return ctor.newInstance(values);
            }
        } catch (Throwable e) {
        }
        return null;
    }

}
