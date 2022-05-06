package me.hhhaiai.refcore;

class ClassWorker {
    static Class<?> forNameByName(String className) {
        try {
            return Class.forName(className);
        } catch (Throwable e) {
        }
        return null;
    }

    static Class<?> forNameByloaders(String className, ClassLoader... loaders) {
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

}
