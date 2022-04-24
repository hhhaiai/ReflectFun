package me.hhhaiai.refcore;

public class JRvoke {


    public static Class<?> getClass(String className) {
        Class<?> clazz = null;
        if (ReUtils.isEmpty(className)) {
            return clazz;
        }
        clazz = forNameByName(className);
        if (clazz == null) {
            return getClass(className, new JRvoke().getClass().getClassLoader());
        }
        return clazz;
    }

    public static Class<?> getClass(String className, ClassLoader... loaders) {
        if (ReUtils.isEmpty(className) || loaders == null || loaders.length < 1) {
            return null;
        }
        return forNameByloaders(className, loaders);
    }


    /***********************************内部实现**********************************/
    private static Class<?> forNameByName(String className) {
        try {
            return Class.forName(className);
        } catch (Throwable e) {
        }
        return null;
    }

    private static Class<?> forNameByloaders(String className, ClassLoader... loaders) {
        for (int i = 0; i < loaders.length; i++) {
            try {
                Class<?> clz = loaders[i].loadClass(className);
                if (clz != null) {
                    return clz;
                }
            } catch (Exception e) {
            }
        }

        return null;
    }

    /***********************************内部实现**********************************/
    private JRvoke() {
    }

}
