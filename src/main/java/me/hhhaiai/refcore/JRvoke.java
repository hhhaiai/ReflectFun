package me.hhhaiai.refcore;

import java.lang.reflect.Method;

/**
 * @Copyright © 2022 sanbo Inc. All rights reserved.
 *
 * @Description: 反射工具类. 支持： 1. 反射类 2. 反射类初始化
 *
 * @Version: 1.0
 *
 * @Create: 2022/04/114 17:59:22
 *
 * @author: sanbo
 */
public class JRvoke {

    ///////////////////////////// 类反射///////////////////////////////////
    public static Class<?> getClass(String className) {
        Class<?> clazz = null;
        if (RUtils.isEmpty(className)) {
            return clazz;
        }
        clazz = ClassWorker.forNameByName(className);
        // maybe wasting some time
        if (clazz == null) {
            return getClass(className, ClassLoader.getSystemClassLoader());
        }
        return clazz;
    }

    public static Class<?> getClass(Object obj) {
        if (RUtils.isNull(obj)) {
            return null;
        }
        return obj.getClass();
    }

    public static Class<?> getClass(String className, ClassLoader... loaders) {
        if (RUtils.isEmpty(className) || loaders == null || loaders.length < 1) {
            return null;
        }
        return ClassWorker.forNameByloaders(className, loaders);
    }

    ///////////////////////////// 类实例化反射///////////////////////////////////

    public static Object getIntance(String className) {
        if (RUtils.isEmpty(className)) {
            return null;
        }
        return getIntance(getClass(className), new Class[] {}, new Object[] {});
    }

    public static Object getIntance(Class<?> clazz) {
        if (RUtils.isNull(clazz)) {
            return null;
        }
        return getIntance(clazz, new Class[] {}, new Object[] {});
    }

    public static Object getIntance(String className, Class[] types, Object[] values) {
        if (RUtils.isEmpty(className)) {
            return null;
        }
        return getIntance(getClass(className), types, values);
    }

    // plan a: find in memory,math,work,or return
    // plan b: work, wait the result.
    // now --> b
    public static Object getIntance(Class<?> clazz, Class[] types, Object[] values) {
        if (RUtils.isNull(clazz)) {
            return null;
        }
        return ConstructorWorker.newInstance(clazz, types, values);
    }
    // android 还有一种create创建的、asInterface

    ///////////////////////////// 方法反射///////////////////////////////////

    /****************** 获取方法 *****************/

    public static Method getMethod(String className, String methodName) {
        if (RUtils.isEmpty(className, methodName)) {
            return null;
        }
        return getMethod(getClass(className), methodName, new Class[] {});
    }

    public static Method getMethod(String className, String methodName, Class<?>... types) {
        if (RUtils.isEmpty(className, methodName)) {
            return null;
        }
        return getMethod(getClass(className), methodName, types);
    }

    public static Method getMethod(Class<?> clazz, String methodName) {
        if (RUtils.isEmpty(methodName) || RUtils.isNull(clazz)) {
            return null;
        }
        return getMethod(clazz, methodName, new Class[] {});
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... types) {
        if (RUtils.isEmpty(methodName) || RUtils.isNull(clazz)) {
            return null;
        }
        return MethodWorker.getMethodImpl(clazz, methodName, types);
    }

    /****************** 获取方法 *****************/
    public static Object invokeMethod(String className, String methodName) {
        return invokeMethod(className, methodName, new Class[] {}, new Object[] {});
    }

    public static Object invokeMethod(String className, String methodName, Class<?>[] types, Object[] values) {
        if (RUtils.isEmpty(className, methodName)) {
            return null;
        }
        return MethodWorker.invokeMethod(null, getMethod(className, methodName, types), values);
    }

    public static Object invokeMethod(Object obj, String methodName) {
        return invokeMethod(obj, methodName, new Class[] {}, new Object[] {});
    }

    public static Object invokeMethod(Object obj, String methodName, Class<?>[] types, Object[] values) {
        if (RUtils.isEmpty(methodName) || RUtils.isNull(obj)) {
            return null;
        }
        return MethodWorker.invokeMethod(obj, getMethod(obj.getClass(), methodName, types), values);
    }
    ///////////////////////////// 变量反射///////////////////////////////////

    /****************** 获取变量 *****************/

    // support static field
    public static Object getFieldValue(String className, String fieldName) {
        if (RUtils.isEmpty(className, fieldName)) {
            return null;
        }
        return getFieldValue(getClass(className), fieldName);
    }

    // support static field
    public static Object getFieldValue(Class<?> clazz, String fieldName) {
        if (RUtils.isEmpty(fieldName) || RUtils.isNull(clazz)) {
            return null;
        }
        return FieldWorker.getFieldValueImpl(null, clazz, fieldName);
    }

    public static Object getField(Object obj, String fieldName) {
        if (RUtils.isNull(obj) || RUtils.isEmpty(fieldName)) {
            return null;
        }
        return FieldWorker.getFieldValueImpl(obj, getClass(obj), fieldName);
    }

    public static boolean setFieldObject(String className, String fieldName, Object instance, Object newValues) {
        if (RUtils.isEmpty(className, fieldName)) {
            return false;
        }
        return setFieldObject(getClass(className), fieldName, instance, newValues);
    }

    public static boolean setFieldObject(Class<?> clazz, String fieldName, Object instance, Object newValues) {
        return FieldWorker.setFieldValueImpl(instance, clazz, fieldName, newValues);
    }

    /*********************************** 内部实现 **********************************/
    private JRvoke() {
    }
}
