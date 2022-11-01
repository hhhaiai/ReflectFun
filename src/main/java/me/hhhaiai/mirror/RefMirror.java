package me.hhhaiai.mirror;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.*;
import java.net.UnknownHostException;

/**
 * @Copyright © 2022 sanbo Inc. All rights reserved.
 * @Description: 整合镜像类/变量/方法
 * @Version: 1.0
 * @Create: 2022-10-27 14:37:37
 * @author: sanbo
 */
public class RefMirror {
    protected static Class<?> mType = null;
    protected static Object mCaller = null;
    protected static RefMirror INSTANCE = null;

    private RefMirror(Object caller, Class type) {
        this.mCaller = caller;
        this.mType = type;
        this.INSTANCE = this;
    }

//    /**
//     * 根据指令类名/对象加载反射类
//     * @param obj
//     * @return
//     */
//    public static RefMirror on(Object obj) {
//        return on(obj, null);
//    }

    /**
     * 加载对应类
     * @param obj
     * @param loaders
     * @return
     */
    public static RefMirror on(Object obj, ClassLoader... loaders) {
        if (obj == null) {
            INSTANCE = new RefMirror(null, null);
            return INSTANCE;
        }
        if (obj instanceof Class) {
            return new RefMirror(null, (Class<?>) obj);
        } else if (obj instanceof String) {
            if (loaders != null && loaders.length > 0) {
                for (int i = 0; i < loaders.length; i++) {
                    Class clazz = findClass((String) obj, loaders[i]);
                    if (clazz != null) {
                        return new RefMirror(null, clazz);
                    }
                }
            }
        } else {
            return new RefMirror(obj, obj.getClass());
        }
        return new RefMirror(null, findClass((String) obj));
    }

//    public <T> RefMethod<T> method(String name) {
//        if (isEmpty(name)) {
//            e(new NullPointerException("method[Class...] is null!"));
//            return new RefMethod<T>(null);
//        }
//        return new RefMethod<T>(getMethod(type(), name));
//    }

    /**
     * 声明式获取方法,在调用时进行调用
     * @param name
     * @param parameterTypes
     * @param <T>
     * @return
     */
    public <T> RefMethod<T> method(String name, Object... parameterTypes) {
        if (isEmpty(name)) {
            e(new NullPointerException("method[Object...] is null!"));
            return new RefMethod<T>(null);
        }
        return new RefMethod<T>(getMethod(type(), name, wrap(parameterTypes)));
    }

    private Class<?>[] wrap(Object... parameterTypes) {
        if (parameterTypes == null || parameterTypes.length < 1) {
            return new Class<?>[0];
        }
        Class<?>[] classes = new Class<?>[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Object classType = parameterTypes[i];
            if (classType instanceof String) {
                classes[i] = findClass((String) classType);
            } else if (classType instanceof Class) {
                classes[i] = (Class<?>) classType;
            } else {
                e(new IllegalAccessException("参数解析失败【wrap】！"));
                return new Class<?>[0];
            }
        }
        return classes;
    }

//    public <T> RefStaticMethod<T> staticMethod(String name) {
//        if (isEmpty(name)) {
//            e(new NullPointerException("method[String...] is null!"));
//            return new RefStaticMethod<T>(null);
//        }
//        return new RefStaticMethod<T>(getMethod(type(), name));
//    }


    /**
     * 声明式获取静态方法,在调用时进行调用
     * @param name
     * @param parameterTypes
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     */
    public <T> RefStaticMethod<T> staticMethod(String name, Object... parameterTypes) {
        if (isEmpty(name)) {
            e(new NullPointerException("method[Object...] is null!"));
            return new RefStaticMethod<T>(null);
        }
        return new RefStaticMethod<T>(getMethod(type(), name, wrap(parameterTypes)));
    }

    public static <T> RefField<T> field(String name) {
        if (isEmpty(name)) {
//            throw new NullPointerException("field is null!");
            return new RefField<T>(null);
        }

        return new RefField<T>(getField(type(), name));
    }

    public <T> RefConstructor<T> constructor(Class<?>... parameterTypes) {
        return new RefConstructor<T>(getConstructor(type(), parameterTypes));
    }


    private static class RefMember<M extends AccessibleObject & Member> {
        M member;

        RefMember(M member) {
            if (member == null) return;
            member.setAccessible(true);
            this.member = member;
        }
    }

    public static class RefMethod<T> extends RefMember<Method> {
        RefMethod(Method method) {
            super(method);
        }

        public T call(Object... args) {
            return call(INSTANCE, args);
        }

        public T call(Object instance, Object... args) {
            try {
                if (member == null) return null;
                return (T) member.invoke(instance, args);
            } catch (Throwable e) {
                e(e);
            }
            return null;
        }

        public T callWithException(Object... args) throws Throwable {
            return callWithException(INSTANCE, args);
        }

        public T callWithException(Object instance, Object... args) throws Throwable {
            if (member == null) return null;
            return (T) member.invoke(instance, args);
        }
    }

    public static class RefStaticMethod<T> extends RefMember<Method> {
        RefStaticMethod(Method method) {
            super(method);
        }

        public T call(Object... args) {
            try {
                if (member == null) return null;
                return (T) member.invoke(null, args);
            } catch (Throwable e) {
                e(e);
            }
            return null;
        }

        public T callWithException(Object... args) throws Throwable {
            if (member == null) return null;
            return (T) member.invoke(null, args);
        }
    }

    public static class RefField<T> extends RefMember<Field> {
        RefField(Field field) {
            super(field);
        }

        public T get(Object instance) {
            try {
                if (member == null) return null;
                return (T) member.get(instance);
            } catch (Throwable e) {
                e(e);
            }
            return null;
        }

        public T get() {
            return get(null);
        }

        public void set(Object instance, Object value) {
            try {
                if (member == null) return;
                setUnFinal(member);
                member.set(instance, value);
            } catch (Throwable e) {
                e(e);
            }
        }

        public void set(Object value) {
            set(null, value);
        }

        public Class<?> getType() {
            if (member == null) return null;
            return member.getType();
        }
    }

    public static class RefConstructor<T> extends RefMember<Constructor<T>> {
        RefConstructor(Constructor<T> constructor) {
            super(constructor);
        }

        public T create(Object... args) {
            try {
                if (member == null) return null;
                return member.newInstance(args);
            } catch (Throwable e) {
                e(e);
                return null;
            }
        }
    }

    public static void setUnFinal(Field field) {
        if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
            Field modifiersField = null;
            try {
                modifiersField = Field.class.getDeclaredField("accessFlags");
            } catch (Throwable e) {
            }
            if (modifiersField == null) {
                try {
                    modifiersField = Field.class.getDeclaredField("modifiers");
                } catch (Throwable e) {
                }
            }
            if (modifiersField != null) {
                try {
                    modifiersField.setAccessible(true);
                    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                } catch (Throwable e) {
                }
            }
        }

    }

    /**
     * 用来表示null的类.
     *
     * @author Lody
     */
    private static class NULL {
    }

    public static boolean isLambdaClass() {
        return isLambdaClass(type());
    }

    /**
     * 取得我们正在反射的对象的类型.
     *
     * @see Object#getClass()
     */
    private static Class<?> type() {
        if (mType == null && mCaller != null) {
            mType = mCaller.getClass();
        }
        if (mType != null) {
            return mType;
        }
        return null;
    }

    /**
     * 将Object数组转换为其类型的数组. 如果对象中包含null,我们用NULL.class代替.
     *
     * @see Object#getClass()
     */
    private static Class<?>[] types(Object... values) {
        if (values == null) {
            return new Class[0];
        }

        Class<?>[] result = new Class[values.length];

        // @todo 区别 int/long等类型
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            result[i] = value == null ? NULL.class : value.getClass();
        }

        return result;
    }

    public static boolean isLambdaClass(Class<?> clazz) {
        return clazz.getName().contains("$$Lambda$");
    }

    public static boolean isProxyClass(Class<?> clazz) {
        return Proxy.isProxyClass(clazz);
    }

    public static <T extends Throwable> void throwUnchecked(Throwable e) throws T {
        throw (T) e;
    }

    public boolean isInstance(Object instance) {
        if (mType == null && mCaller != null) {
            mType = mCaller.getClass();
        }
        if (mType == null) {
            throw new NullPointerException("class is null!");
        }
        return mType.isInstance(instance);
    }

    /**
     * 根据类名加载对应的类
     * @param className
     * @return
     */
    public static Class findClass(String className) {
        return findClass(className, null);
    }

    public static Class findClass(String className, ClassLoader loader) {
        try {
            // forName是使用BootClassLoader.getInstance()加载对应类
            if (loader == null) {
                return Class.forName(className);
            } else {
                return Class.forName(className, true, loader);
            }
        } catch (Throwable e) {
            e(e);
        }
        if (loader == null) {
            try {
                return ClassLoader.getSystemClassLoader().loadClass(className);
            } catch (Throwable e) {
                e(e);
            }
            try {
                return RefMirror.class.getClassLoader().loadClass(className);
            } catch (Throwable e) {
                e(e);
            }
        } else {
            try {
                return loader.loadClass(className);
            } catch (Throwable e) {
                e(e);
            }
        }

        return null;
    }

    private Constructor getConstructor(Class<?> clazz, Class<?>[] types) {
        if (clazz == null) {
            return null;
        }
        Constructor constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor(types);
        } catch (Throwable e) {
            e(e);
        }
        try {
            if (constructor == null) {
                constructor = clazz.getConstructor(types);
            }
        } catch (Throwable e) {
            e(e);
        }
        if (constructor != null) {
            //版本23以后
            //我们不能使用 constructor.setAccessible(true);谷歌会限制
            // AccessibleObject.setAccessible(new AccessibleObject[]{constructor}, true);
            // 或者修改标签： Field override = AccessibleObject.class.getDeclaredField(
            //                        Build.VERSION.SDK_INT == Build.VERSION_CODES.M ? "flag" : "override");
            constructor.setAccessible(true);
        }
        return constructor;
    }

    public static Field getField(Class clazz, String fieldName) {
        if (clazz == null || isEmpty(fieldName)) {
            return null;
        }
        Field field = null;
        while (clazz != Object.class) {
            try {
                field = clazz.getDeclaredField(fieldName);

                if (field != null) {
                    accessible(field);
                    return field;
                }
            } catch (Throwable e) {
            }
            clazz = clazz.getSuperclass();
        }
        return field;
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... types) {
        if (clazz == null || isEmpty(methodName)) {
            return null;
        }
        Method method = null;
        while (clazz != Object.class) {
            try {
                method = clazz.getDeclaredMethod(methodName, types);
                if (method != null) {
                    accessible(method);
                    return method;
                }
            } catch (Throwable e) {
            }
            clazz = clazz.getSuperclass();
        }
        return method;
    }

    public static <T extends AccessibleObject> T accessible(T accessible) {
        if (accessible == null) {
            return null;
        }

        if (accessible instanceof Member) {
            Member member = (Member) accessible;
            if (Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
                return accessible;
            }
        }
        if (!accessible.isAccessible()) {
            accessible.setAccessible(true);
        }
        return accessible;
    }


    private static void setFinalFieldReadable(Field field) throws NoSuchFieldException, IllegalAccessException {
        int modify = field.getModifiers();
        if (Modifier.isFinal(modify)) {
            Field modifiersField = Field.class.getDeclaredField("accessFlags");
            if (modifiersField == null) {
                modifiersField = Field.class.getDeclaredField("modifiers");
            }
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, modify & ~Modifier.FINAL);
        }
    }

    /**************************************工具方法***********************************/
    public static void e(Throwable e) {
        System.err.println(getStackTraceString(e));
    }

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

}
