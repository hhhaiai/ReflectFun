package me.hhhaiai.refcore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldWorker {

    /**
     * 获取变量值,支持静态变量和内部公开、私有变量
     *
     * @param instance
     * @param clazz
     * @param fieldName
     *
     * @return
     */
    public static Object getFieldValueImpl(Object instance, Class<?> clazz, String fieldName) {
        try {
            Field addr = getField(clazz, fieldName);
            if (addr != null) {
                return addr.get(instance);
            }
        } catch (Throwable e) {
        }
        return null;
    }

    public static Field getField(Class clazz, String fieldName) {
        if (clazz == null || RUtils.isEmpty(fieldName)) {
            return null;
        }
        Field field = null;
        while (clazz != Object.class) {
            try {
                field = clazz.getDeclaredField(fieldName);

                if (field != null) {
                    // jdk9 canAccess(Object)
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    return field;
                }
            } catch (Throwable e) {
            }
            clazz = clazz.getSuperclass();
        }
        return field;
    }


    public static boolean setFieldValueImpl(Object instance, Class<?> clazz, String fieldName, Object newValues) {

        try {
            if (clazz == null && instance != null) {
                clazz = instance.getClass();
            }

            if (RUtils.isNull(newValues) || RUtils.isNull(clazz) || RUtils.isEmpty(fieldName)) {
                return false;
            }
            Field addr = getField(clazz, fieldName);
            if (addr != null) {
                if (RUtils.isFinal(addr)) {
                    setFinalFieldReadable(addr);
                }
                addr.set(instance, newValues);
                return true;
            }
        } catch (Throwable e) {
        }
        return false;
    }


    // support android and java
    private static void setFinalFieldReadable(Field field) throws NoSuchFieldException, IllegalAccessException {
        int modify = field.getModifiers();
        if (Modifier.isFinal(modify)) {
            Field modifiersField = getField(Field.class, "accessFlags");
            if (modifiersField == null) {
                modifiersField = getField(Field.class, "modifiers");
            }
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, modify & ~Modifier.FINAL);
        }
    }


}
