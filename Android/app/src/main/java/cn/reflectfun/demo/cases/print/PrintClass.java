package cn.reflectfun.demo.cases.print;

import me.hhhaiai.refcore.Rvoke;
import me.hhhaiai.refcore.utils.RLog;

/**
 * 打印方法调用
 */
public class PrintClass {

    public static void hoo(String className) {
        RLog.i(">>>>>>>>>>>>>>>>>>>class [ " + className + " ]<<<<<<<<<<<<<<<<<");
        try {
            RLog.i("\t\t class.forname: " + Class.forName(className));
        } catch (Throwable e) {
            RLog.i("\t\t class.forname: null");
            RLog.v(e);
        }
        try {
            RLog.i("\t\t loadClass: " + PrintClass.class.getClassLoader().loadClass(className));
        } catch (Throwable e) {
            RLog.i("\t\t loadClass: null");
            RLog.v(e);
        }

        try {
            RLog.i("\t\t Rvoke: " + Rvoke.getClass(className));
        } catch (Throwable e) {
            RLog.i("\t\t Rvoke: null");
            RLog.v(e);
        }
    }
}
