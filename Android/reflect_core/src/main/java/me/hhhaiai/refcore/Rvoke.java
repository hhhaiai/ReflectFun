package me.hhhaiai.refcore;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import me.hhhaiai.refcore.fkhide.HideApiImpl;
import me.hhhaiai.refcore.utils.RLog;


/**
 * @author miqt & sanbo
 * @Description: 反射工具类
 */
public class Rvoke {


    private static Method forName = null;
    private static Method getDeclaredMethod = null;
    private static Method getMethod = null;
    private static Method getDeclaredField = null;
    private static Method getField = null;
    private static Method getDeclaredConstructor = null;
    private static Method getConstructor = null;
    private static Method newInstance = null;


    /********************* get instance begin **************************/

    static {
        try {
            forName = Class.class.getDeclaredMethod("forName", String.class);
            // invoke = Method.class.getMethod("invoke", Object.class, Object[].class);
            // 反射获取方法
            getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
            getMethod = Class.class.getDeclaredMethod("getMethod", String.class, Class[].class);

            // 反射获取变量
            getDeclaredField = Class.class.getDeclaredMethod("getDeclaredField", String.class);
            getField = Class.class.getDeclaredMethod("getField", String.class);

            // 反射实例化代码
            getDeclaredConstructor = Class.class.getDeclaredMethod("getDeclaredConstructor", Class[].class);
            getConstructor = Class.class.getDeclaredMethod("getConstructor", Class[].class);
            newInstance = Constructor.class.getDeclaredMethod("newInstance", Object[].class);
        } catch (Throwable igone) {
            RLog.e(igone);
        }
        if (Build.VERSION.SDK_INT > 27) {
            HideApiImpl.hide(forName, getDeclaredMethod);
        }
    }

    private Rvoke() {
    }

    public static Object invokeStaticMethod(String clazzName, String methodName) {
        return invokeStaticMethod(getClass(clazzName), methodName, new Class<?>[]{}, new Object[]{});
    }

    public static Object invokeStaticMethod(Class clazz, String methodName) {
        return invokeStaticMethod(clazz, methodName, new Class<?>[]{}, new Object[]{});
    }

    public static Object invokeStaticMethod(String clazzName, String methodName, Class<?>[] argsClass, Object[] args) {
        return invokeStaticMethod(getClass(clazzName), methodName, argsClass, args);
    }

    public static Object invokeStaticMethod(Class clazz, String methodName, Class<?>[] argsClass, Object[] args) {
        return getMethodProcess(clazz, methodName, null, argsClass, args);
    }

    public static Object invokeObjectMethod(Object o, String methodName) {
        return invokeObjectMethod(o, methodName, new Class[]{}, new Object[]{});
    }

    public static Object invokeObjectMethod(Object o, String methodName, String[] argsClassNames, Object[] args) {
        return invokeObjectMethod(o, methodName, converStringToClass(argsClassNames), args);
    }

    public static Object invokeObjectMethod(Object o, String methodName, Class<?>[] argsClass, Object[] args) {
        if (o == null || TextUtils.isEmpty(methodName)) {
            return null;
        }
        return getMethodProcess(o.getClass(), methodName, o, argsClass, args);
    }

    private static Object getMethodProcess(Class clazz, String methodName, Object o, Class<?>[] types, Object[] values) {
        if (clazz == null || TextUtils.isEmpty(methodName)) {
            return null;
        }
        if (!(types != null && values != null && types.length == values.length)) {
            return null;
        }
        if (types == null || values == null) {
            types = new Class[]{};
            values = new Object[]{};
        }
        return goInvoke(getMethod(clazz, methodName, types), o, values);
    }

    public static Method getMethod(String clazzName, String methodName, Class<?>... types) {
        return getMethod(getClass(clazzName), methodName, types);
    }

    public static Method getMethod(String clazzName, String methodName) {
        return getMethod(getClass(clazzName), methodName, new Class<?>[]{});
    }

    public static Method getMethod(Class clazz, String methodName) {
        return getMethod(clazz, methodName, new Class<?>[]{});
    }

    public static Method getMethod(Class clazz, String methodName, Class<?>... types) {
        Method method = null;
        try {
            if (clazz == null || TextUtils.isEmpty(methodName)) {
                return method;
            }
            method = (Method) goInvoke(getDeclaredMethod, clazz, methodName, types);
            if (method == null) {
                method = (Method) goInvoke(getMethod, clazz, methodName, types);
            }
            if (method == null) {
                method = getMethodB(clazz, methodName, types);
            }
        } catch (Throwable igone) {
            RLog.e(igone);
            if (method == null) {
                method = getMethodB(clazz, methodName, types);
            }
        }
        if (method != null) {
            method.setAccessible(true);
        }
        return method;
    }

    private static Method getMethodB(Class clazz, String methodName, Class<?>[] parameterTypes) {
        Method method = null;
        try {
            if (method == null) {
                try {
                    method = clazz.getDeclaredMethod(methodName, parameterTypes);
                } catch (Throwable igone) {
                    RLog.e(igone);
                }
            }
            if (method == null) {
                try {
                    method = clazz.getMethod(methodName, parameterTypes);
                } catch (Throwable igone) {
                    RLog.e(igone);
                }
            }
            if (method != null) {
                method.setAccessible(true);
                return method;
            }
        } catch (Throwable igone) {
            RLog.e(igone);
        }
        return null;
    }

    /**
     * 获取构造函数
     *
     * @param clazzName
     * @return
     */
    public static Object newInstance(String clazzName) {
        return newInstance(clazzName, new Class[]{}, new Object[]{});
    }

    public static Object newInstance(Class clazzName) {
        return newInstance(clazzName, new Class[]{}, new Object[]{});
    }

    public static Object newInstance(String clazzName, Class[] types, Object[] values) {
        return newInstance(getClass(clazzName), types, values);
    }

    public static Object newInstance(Class clazz, Class[] types, Object[] values) {
        try {
            if (clazz == null) {
                return null;
            }
            if (!(types != null && values != null && types.length == values.length)) {
                return null;
            }
            Constructor ctor = null;
            if (types == null || types.length == 0) {
                ctor = (Constructor) goInvoke(getDeclaredConstructor, clazz, new Object[]{null});
                if (ctor == null) {
                    ctor = (Constructor) goInvoke(getConstructor, clazz, new Object[]{null});
                }
            } else {
                ctor = (Constructor) goInvoke(getDeclaredConstructor, clazz, new Object[]{types});
                if (ctor == null) {
                    ctor = (Constructor) goInvoke(getConstructor, clazz, new Object[]{types});
                }
            }
            if (ctor != null) {
                ctor.setAccessible(true);
                if (types == null || types.length == 0) {
                    return goInvoke(newInstance, ctor, new Object[]{null});
                } else {
                    return goInvoke(newInstance, ctor, new Object[]{values});
                }
            } else {
                return newInstanceImplB(clazz, types, values);
            }
        } catch (Throwable igone) {
            RLog.e(igone);
            return newInstanceImplB(clazz, types, values);
        }
    }

    private static Object newInstanceImplB(Class clazz, Class[] types, Object[] values) {
        try {
            Constructor ctor = null;

            try {
                ctor = clazz.getDeclaredConstructor(types);
            } catch (Throwable igone) {
                RLog.e(igone);
            }

            if (ctor == null) {
                try {
                    ctor = clazz.getConstructor(types);
                } catch (Throwable igone) {
                    RLog.e(igone);
                }
            }
            if (ctor != null) {
                ctor.setAccessible(true);
                return ctor.newInstance(values);
            }
        } catch (Throwable igone) {
            RLog.e(igone);
        }
        return null;
    }

//    /**
//     * 通过名称获取类.
//     *
//     * @param name
//     * @return
//     */
//    public static Class getClass(String name) {
//        Class result = null;
//        try {
//            if (TextUtils.isEmpty(name)) {
//                return result;
//            }
//                result = getClassByForName(name);
//            if (result == null) {
//                result = getClassByf(name);
//            }
//        } catch (Throwable igone) {
//                    if (isLog) {
//                        RLog.e(igone);
//                    }
//        }
//
//        return result;
//    }

    public static Object getFieldValue(Object o, String fieldName) {
        if (o == null) {
            return null;
        }
        return getFieldValueImpl(o.getClass(), fieldName, o);
    }

    public static Object getStaticFieldValue(String className, String fieldName) {
        return getFieldValueImpl(getClass(className), fieldName, null);
    }

    public static Object getStaticFieldValue(Class clazz, String fieldName) {
        return getFieldValueImpl(clazz, fieldName, null);
    }

    public static void setStaticFieldValue(String className, String fieldName, Object value) {
        setFieldValueImpl(null, getClass(className), fieldName, value);
    }

    public static void setStaticFieldValue(Class clazz, String fieldName, Object value) {
        if (clazz == null || TextUtils.isEmpty(fieldName)) {
            return;
        }
        setFieldValueImpl(null, clazz, fieldName, value);
    }

    public static void setFieldValue(Object o, String fieldName, Object value) {
        if (o == null) {
            return;
        }
        setFieldValueImpl(o, o.getClass(), fieldName, value);
    }

    private static void setFieldValueImpl(Object o, Class<?> clazz, String fieldName, Object value) {
        try {
            Field field = getUpdateableFieldImpl(clazz, fieldName);
            if (field != null) {
                field.set(o, value);
            }
        } catch (Throwable igone) {
            RLog.e(igone);
        }
    }

    private static Object getFieldValueImpl(Class clazz, String fieldName, Object o) {
        try {
            Field field = getUpdateableFieldImpl(clazz, fieldName);
            if (field != null) {
                return field.get(o);
            }
        } catch (Throwable igone) {
            RLog.e(igone);
        }
        return null;
    }

    public static Field getField(Object o, String fieldName) {
        if (o == null) {
            return null;
        }
        return getField(o.getClass(), fieldName);
    }

    public static Field getField(String className, String fieldName) {
        return getField(getClass(className), fieldName);
    }

    public static Field getField(Class clazz, String fieldName) {
        return getUpdateableFieldImpl(clazz, fieldName);
    }

    public static Field getStaticField(String className, String fieldName) {
        return getField(getClass(className), fieldName);
    }

    public static Field getStaticField(Class clazz, String fieldName) {
        return getUpdateableFieldImpl(clazz, fieldName);
    }

    //内部元反射获取变量，无须关注异常，不打印日志
    private static Field getUpdateableFieldImpl(Class clazz, String fieldName) {
        Field field = null;
        try {
            if (clazz == null || TextUtils.isEmpty(fieldName)) {
                return field;
            }

            field = (Field) goInvoke(getDeclaredField, clazz, fieldName);
            if (field == null) {
                field = (Field) goInvoke(getField, clazz, fieldName);
            }

            if (field == null) {
                return getFieldImplB(clazz, fieldName);
            } else {
                field.setAccessible(true);
                return field;
            }
        } catch (Throwable igone) {
            RLog.e(igone);
            return getFieldImplB(clazz, fieldName);
        }
    }

    //内部常规反射获取变量，无须关注异常，不打印日志
    private static Field getFieldImplB(Class clazz, String fieldName) {
        Field field = null;
        try {
            if (clazz == null || TextUtils.isEmpty(fieldName)) {
                return field;
            }

            if (field == null) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                } catch (Throwable igone) {
                    RLog.e(igone);
                }
            }
            if (field == null) {
                try {
                    field = clazz.getField(fieldName);
                } catch (Throwable igone) {
                    RLog.e(igone);
                }
            }
            if (field != null) {
                field.setAccessible(true);
                return field;
            }
        } catch (Throwable igone) {
            RLog.e(igone);
        }

        return null;
    }

    public static Class getClass(String name, Object... loader) {
        Class result = null;
        try {
            if (TextUtils.isEmpty(name)) {
                return result;
            }
            result = getaClassByLoader(name, loader);
            if (result == null) {
                result = getClassByForName(name);
            }
            if (result == null) {
//                result = getaClassByLoader(name, ClazzUtils.class.getClassLoader());
                Object o = invokeObjectMethod(name.getClass(), "getClassLoader");
                if (o != null) {
                    result = getaClassByLoader(name, o);
                } else {
                    result = null;
                }
            }
            if (result == null) {
                result = getClassByf(name);
            }
        } catch (Throwable igone) {
            RLog.e(igone);
        }

        return result;
    }

    private static Class getaClassByLoader(String className, Object... loader) {
        Class result = null;
        if (loader != null && loader.length > 0) {
            for (int i = 0; i < loader.length; i++) {
                try {
                    if (result == null) {
                        result = (Class) invokeObjectMethod(loader[i], "loadClass", new Class[]{String.class}, new Object[]{className});
                    } else {
                        return result;
                    }
                } catch (Throwable igone) {
                    RLog.e(igone);
                }
            }
        }
        return result;
    }

    private static Class<?> getClassByf(String name) {
        try {
            return Class.forName(name);
        } catch (Throwable igone) {
            RLog.e(igone);
        }
        return null;
    }

    private static Class getClassByForName(String name) {
        try {
            return (Class) goInvoke(forName, null, name);
        } catch (Throwable igone) {
            RLog.e(igone);
        }
        return null;
    }

    /**
     * 公用的反射方法， 执行invoke方法
     *
     * @param method    调用谁的方法
     * @param obj       若静态则为null,非静态则为对象
     * @param argsValue 参数
     * @return
     */
    private static Object goInvoke(Method method, Object obj, Object... argsValue) {
        try {
            if (method != null) {
                return method.invoke(obj, argsValue);
            }
        } catch (Throwable igone) {
            RLog.e(igone);
        }
        return null;
    }


    private static Class[] converStringToClass(String[] argsClassNames) {
        if (argsClassNames != null) {
            Class[] argsClass = new Class[argsClassNames.length];
            for (int i = 0; i < argsClassNames.length; i++) {
                try {
                    argsClass[i] = getClass(argsClassNames[i]);
                } catch (Throwable igone) {
                    RLog.e(igone);
                }
            }
            return argsClass;
        }
        return new Class[]{};
    }

    public static Object getDexClassLoader(Context context, String path) {
        try {
            String dc = "dalvik.system.DexClassLoader";
            Class c = getClass("java.lang.ClassLoader");
            if (c != null) {
//            Class[] types = new Class[]{String.class, String.class, String.class, ClassLoader.class};
                Class[] types = new Class[]{String.class, String.class, String.class, c};
                Object[] values = new Object[]{path, context.getCacheDir().getAbsolutePath(), null, invokeObjectMethod(context, "getClassLoader")};
                return newInstance(dc, types, values);
            }
        } catch (Throwable igone) {
            RLog.e(igone);
        }
        return null;
    }

    /**
     * get Build's static field
     *
     * @param fieldName
     * @return
     */
    public static String getBuildStaticField(String fieldName) {
        try {
            Field fd = getUpdateableFieldImpl(Build.class, fieldName);
            if (fd != null) {
                fd.setAccessible(true);
                return (String) fd.get(null);
            }
        } catch (Throwable igone) {
            RLog.e(igone);
        }
        return "";
    }

    /**
     * 反射SystemProperties.get(String).获取数据是default.prop中的数据.
     * api 14-29均有
     *
     * @param key
     * @return
     */
    public static Object getDefaultProp(String key) {
        if (TextUtils.isEmpty(key)) {
            return "";
        }
        return invokeStaticMethod("android.os.SystemProperties", "get", new Class[]{String.class}, new Object[]{key});
    }
}
