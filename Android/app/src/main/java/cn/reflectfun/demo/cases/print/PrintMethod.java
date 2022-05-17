package cn.reflectfun.demo.cases.print;

import me.hhhaiai.refcore.Rvoke;
import me.hhhaiai.refcore.utils.RLog;

/**
 * 打印方法调用
 */
public class PrintMethod {

    public static void hoo(String clazzName, String methodName, Class<?>... ctype) {
        hoo(Rvoke.getClass(clazzName), methodName, ctype);
    }

    public static void hoo(Class<?> clazz, String methodName, Class<?>... ctype) {
        RLog.i(
                ">>>>>>>>>>>>>>>>>>>method [ "
                        + clazz.getName()
                        + "."
                        + methodName
                        + " ]<<<<<<<<<<<<<<<<<");
        try {
            RLog.i("\t\t getMethod: " + clazz.getMethod(methodName));
        } catch (Throwable e) {
            RLog.i("\t\t getMethod: null");
            RLog.v(e);
        }
        try {
            RLog.i("\t\t getDeclaredMethod: " + clazz.getDeclaredMethod(methodName));
        } catch (Throwable e) {
            RLog.i("\t\t getDeclaredMethod: null");
            RLog.v(e);
        }
        try {
            RLog.i("\t\t Rvoke: " + Rvoke.getMethod(clazz, methodName));
        } catch (Throwable e) {
            RLog.i("\t\t Rvoke: null");

            RLog.v(e);
        }
    }
}
