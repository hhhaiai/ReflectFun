package me.hhhaiai.refcore.tests;

import me.hhhaiai.refcore.JRvoke;
import org.junit.Test;

public class TT {

    @Test
    public void testMethod() {
        //        Method getName = JRvoke.getMethod("me.demo.tess.Pria", "getName");
        //        Method getB =JRvoke. getMethod("me.demo.tess.PublicB", "getB");
        //        System.out.println("getName:"+getName);
        //        System.out.println("getB:"+getB);

        //        PublicB o = new PublicB();
        //        System.out.println(o.getB());
        System.out.println(JRvoke.invokeMethod("me.demo.tess.PublicB", "getA"));
        System.out.println(JRvoke.getMethod("me.demo.tess.PublicB", "getB"));
        //  not instance, cann't find public/private not static args
        System.out.println(JRvoke.invokeMethod("me.demo.tess.PublicB", "getB"));
        System.out.println(JRvoke.invokeMethod("me.demo.tess.PublicB", "getSss"));
        // instance
        System.out.println(JRvoke.invokeMethod(JRvoke.getIntance("me.demo.tess.PublicB"), "getB"));
        System.out.println(
                JRvoke.invokeMethod(JRvoke.getIntance("me.demo.tess.PublicB"), "getSss"));
    }
}
