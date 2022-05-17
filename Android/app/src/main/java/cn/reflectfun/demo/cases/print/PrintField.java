package cn.reflectfun.demo.cases.print;

import me.hhhaiai.refcore.Rvoke;
import me.hhhaiai.refcore.utils.RLog;

/**
 * 打印方法调用
 */
public class PrintField {

    public static void hoo(String clazzName, String methodName, Class<?>... ctype) {
        hoo(Rvoke.getClass(clazzName), methodName, ctype);
    }

    public static void hoo(Class<?> clazz, String fieldName, Class<?>... ctype) {

        RLog.i(
                ">>>>>>>>>>>>>>>>>>>field [ "
                        + clazz.getName()
                        + "."
                        + fieldName
                        + " ]<<<<<<<<<<<<<<<<<");
        try {
            RLog.i("\t\t getField: " + clazz.getField(fieldName));
        } catch (Throwable e) {
            RLog.i("\t\t getField: null");
            RLog.v(e);
        }
        try {
            RLog.i("\t\t getDeclaredField: " + clazz.getDeclaredField(fieldName));
        } catch (Throwable e) {
            RLog.i("\t\t getDeclaredField: null");
            RLog.v(e);
        }
        try {
            RLog.i("\t\t Rvoke.getField: " + Rvoke.getField(clazz, fieldName));
        } catch (Throwable e) {
            RLog.i("\t\t Rvoke.getField: null");

            RLog.v(e);
        }
        try {
            RLog.i("\t\t Rvoke.getStaticField: " + Rvoke.getStaticField(clazz, fieldName));
        } catch (Throwable e) {
            RLog.i("\t\t Rvoke.getStaticField: null");

            RLog.v(e);
        }
    }
}
