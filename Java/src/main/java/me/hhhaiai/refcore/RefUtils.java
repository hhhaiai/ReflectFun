package me.hhhaiai.refcore;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Comparator;

/**
 * @Copyright © 2022 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2022/4/24 5:19 PM
 * @author: sanbo
 */
class RefUtils {

    static boolean isEmpty(CharSequence... strs) {
        if (strs == null) {
            return true;
        }
        for (CharSequence str : strs) {
            if (str == null || str.length() == 0) {
                return true;
            }
        }
        return false;
    }

    static boolean isStatic(Method method) {
        int modifiers = method.getModifiers();
        return Modifier.isStatic(modifiers);
    }

    static boolean isFinal(Method method) {
        int modifiers = method.getModifiers();
        return Modifier.isFinal(modifiers);
    }

    static boolean isPublic(Method method) {
        int modifiers = method.getModifiers();
        return Modifier.isPublic(modifiers);
    }

    static boolean isPrivate(Method method) {
        int modifiers = method.getModifiers();
        return Modifier.isPrivate(modifiers);
    }

    // @TODO need test, getMethod or getDeclaredMethod
    static boolean isProtected(Method method) {
        int modifiers = method.getModifiers();
        return Modifier.isProtected(modifiers);
    }

    static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null) throw new NullPointerException(message);
        return obj;
    }

    public static <T> T requireNonNullElse(T obj, T defaultObj) {
        return (obj != null) ? obj : requireNonNull(defaultObj, "defaultObj");
    }

    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        return (a == b) ? 0 : c.compare(a, b);
    }
}
